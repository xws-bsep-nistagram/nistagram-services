package rs.ac.uns.ftn.nistagram.user.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.user.domain.user.RegistrationRequest;
import rs.ac.uns.ftn.nistagram.user.domain.user.User;
import rs.ac.uns.ftn.nistagram.user.http.auth.AuthClient;
import rs.ac.uns.ftn.nistagram.user.http.auth.Credentials;

@Service
public class RegistrationService {

    private final AuthClient authClient;

    public RegistrationService(AuthClient authClient) {
        this.authClient = authClient;
    }

    public User register(RegistrationRequest request) {
        Credentials credentials = new Credentials(
                request.getUsername(),
                request.getPassword(),
                request.getEmail()
        );
        authClient.register(credentials);
        //Save data in this service

        return new User();
    }

}
