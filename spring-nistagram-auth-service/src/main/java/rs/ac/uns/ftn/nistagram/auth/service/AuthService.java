package rs.ac.uns.ftn.nistagram.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.auth.domain.AuthRequest;
import rs.ac.uns.ftn.nistagram.auth.domain.AuthToken;
import rs.ac.uns.ftn.nistagram.auth.domain.RegistrationRequest;
import rs.ac.uns.ftn.nistagram.auth.infrastructure.JwtEncoder;
import rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions.JwtEncryptionException;

import java.io.IOException;
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

    /**
     * Encrypts user details into JWT. Edit this to include additional fields/claims in a token.
     * @param userDetails source of details to be encrypted.
     * @return Encrypted JWT.
     */
    private String encryptDetails(UserDetails userDetails) {
        try {
            return encoder.encrypt(
                    new AuthToken(
                            userDetails.getUsername(),
                            userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                                    .collect(Collectors.toList())));
        } catch (IOException e) {
            throw new JwtEncryptionException();
        }
    }

}
