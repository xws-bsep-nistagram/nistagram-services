package rs.ac.uns.ftn.nistagram.auth.service;

import com.auth0.jwt.interfaces.Claim;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.auth.domain.*;
import rs.ac.uns.ftn.nistagram.auth.http.user.UserClient;
import rs.ac.uns.ftn.nistagram.auth.infrastructure.JwtEncoder;
import rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions.auth.BannedException;
import rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions.auth.JwtEncryptionException;
import rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions.auth.JwtException;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final CredentialsService credentialsService;
    private final MailService mailService;
    private final PasswordResetService passwordResetService;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder encoder;
    private final UserClient userClient;

    public String authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(authRequest.convert());

        bannedCheck(authRequest.getUsername());

        UserDetails userDetails = getUser(authRequest.getUsername());

        log.info("Successfully authenticated user '{}' with roles: {}", userDetails.getUsername(), userDetails.getAuthorities());

        return encryptDetails(userDetails);
    }


    @Transactional(readOnly = true)
    public UserDetails getUser(String username) {
        return credentialsService.loadUserByUsername(username);
    }


    @Transactional
    public String register(RegistrationRequest registrationRequest) {
        log.info("New registration request with username '{}'", registrationRequest.getUsername());
        registrationRequest.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        Credentials credentials = credentialsService.registerUser(registrationRequest);

        log.info("Sending activation mail to '{}'", registrationRequest.getEmail());
        mailService.sendActivationMessage(credentials);

        log.debug("User with username '{}' created", registrationRequest.getUsername());
        return encryptDetails(credentials);
    }

    private String encryptDetails(UserDetails userDetails) {
        try {
            return encoder.encode(
                    new AuthToken(
                            userDetails.getUsername(),
                            userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                                    .collect(Collectors.toList())));
        } catch (IOException e) {
            throw new JwtEncryptionException();
        }
    }

    @Transactional
    public AuthToken getAuthToken(String jwt) {
        if (jwt == null) {
            throw new JwtException("JWT cannot be null!");
        }
        String username = getUsernameFromJwt(jwt);
        UserDetails found = credentialsService.loadUserByUsername(username);
        log.info("Retrieved user '{}' from jwt token", found.getUsername());
        return new AuthToken(
                found.getUsername(),
                found.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
        );
    }

    private String getUsernameFromJwt(String jwt) {
        Map<String, Claim> claims = this.encoder.decode(jwt);
        return claims.get("username").asString();
    }

    private void bannedCheck(String username) {
        if (userClient.isBanned(username).isProfileBanned())
            throw new BannedException("Account is banned");
    }

    public void activate(String uuid) {
        if (!credentialsService.existsByUuid(uuid))
            throw new EntityNotFoundException("An error occurred while registering your account. Please try again");

        credentialsService.activate(uuid);
    }

    public void requestPasswordReset(PasswordResetRequest resetRequest) {
        passwordResetService.requestPasswordReset(resetRequest);
    }

    public void resetPassword(PasswordResetBundle bundle) {
        bundle.setPassword(passwordEncoder.encode(bundle.getPassword()));
        passwordResetService.resetPassword(bundle);
    }

    public void registerAgent(String username) {
        credentialsService.addUserRole(username, "ROLE_AGENT");
    }


}
