package rs.ac.uns.ftn.nistagram.auth.service;

import com.auth0.jwt.interfaces.Claim;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.auth.domain.AuthRequest;
import rs.ac.uns.ftn.nistagram.auth.domain.AuthToken;
import rs.ac.uns.ftn.nistagram.auth.domain.RegistrationRequest;
import rs.ac.uns.ftn.nistagram.auth.infrastructure.JwtEncoder;
import rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions.JwtEncryptionException;
import rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions.JwtException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final CredentialsService credentialsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder encoder;

    public AuthService(AuthenticationManager authenticationManager, CredentialsService credentialsService, PasswordEncoder passwordEncoder, JwtEncoder encoder) {
        this.authenticationManager = authenticationManager;
        this.credentialsService = credentialsService;
        this.passwordEncoder = passwordEncoder;
        this.encoder = encoder;
    }

    public String authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(authRequest.convert());

        UserDetails userDetails = credentialsService.loadUserByUsername(authRequest.getUsername());

        return encryptDetails(userDetails);
    }

    public String register(RegistrationRequest registrationRequest) {
        registrationRequest.hashPassword(passwordEncoder::encode);
        UserDetails userDetails = credentialsService.registerUser(registrationRequest);
        return encryptDetails(userDetails);
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
}
