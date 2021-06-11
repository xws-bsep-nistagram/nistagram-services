package rs.ac.uns.ftn.nistagram.auth.service;

import com.auth0.jwt.interfaces.Claim;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.auth.domain.AuthRequest;
import rs.ac.uns.ftn.nistagram.auth.domain.AuthToken;
import rs.ac.uns.ftn.nistagram.auth.domain.Credentials;
import rs.ac.uns.ftn.nistagram.auth.domain.RegistrationRequest;
import rs.ac.uns.ftn.nistagram.auth.infrastructure.JwtEncoder;
import rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions.JwtEncryptionException;
import rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions.JwtException;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final CredentialsService credentialsService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder encoder;

    public AuthService(AuthenticationManager authenticationManager,
                       CredentialsService credentialsService,
                       MailService mailService,
                       PasswordEncoder passwordEncoder,
                       JwtEncoder encoder) {
        this.authenticationManager = authenticationManager;
        this.credentialsService = credentialsService;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
        this.encoder = encoder;
    }

    public String authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(authRequest.convert());
        UserDetails userDetails = credentialsService.loadUserByUsername(authRequest.getUsername());

        log.info("Successfully authenticated user '{}' with roles: {}", userDetails.getUsername(), userDetails.getAuthorities());

        return encryptDetails(userDetails);
    }

    @Transactional
    public String register(RegistrationRequest registrationRequest) {
        log.info("New registration request with username '{}'", registrationRequest.getUsername());
        registrationRequest.hashPassword(passwordEncoder::encode);
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

    public void activate(String uuid) {
        credentialsService.activate(uuid);
    }
}
