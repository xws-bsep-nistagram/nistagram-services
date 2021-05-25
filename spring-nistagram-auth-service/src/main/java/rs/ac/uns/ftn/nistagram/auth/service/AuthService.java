package rs.ac.uns.ftn.nistagram.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.auth.domain.AuthRequest;
import rs.ac.uns.ftn.nistagram.auth.domain.AuthToken;
import rs.ac.uns.ftn.nistagram.auth.infrastructure.JwtEncoder;
import rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions.JwtEncryptionException;

import java.io.IOException;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtEncoder encoder;

    public AuthService(AuthenticationManager authenticationManager,
                       UserDetailsService userDetailsService,
                       JwtEncoder encoder) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }

    public String authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(authRequest.convert());

        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

        try {
            return encoder.encrypt(new AuthToken(userDetails.getUsername()));
        } catch (IOException e) {
            throw new JwtEncryptionException();
        }
    }

}
