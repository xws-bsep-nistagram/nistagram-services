package rs.ac.uns.ftn.nistagram.user.service;

import feign.FeignException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.user.domain.user.RegistrationRequest;
import rs.ac.uns.ftn.nistagram.user.domain.user.User;
import rs.ac.uns.ftn.nistagram.user.http.auth.AuthClient;
import rs.ac.uns.ftn.nistagram.user.http.auth.Credentials;
import rs.ac.uns.ftn.nistagram.user.infrastructure.exceptions.RegistrationException;
import rs.ac.uns.ftn.nistagram.user.repository.UserRepository;

import java.nio.charset.StandardCharsets;

@Service
public class RegistrationService {

    private final AuthClient authClient;
    private final UserRepository repository;

    public RegistrationService(AuthClient authClient, UserRepository repository) {
        this.authClient = authClient;
        this.repository = repository;
    }

    @Transactional
    public String register(RegistrationRequest request) {
        Credentials credentials = new Credentials(
                request.getUsername(),
                request.getPassword(),
                request.getEmail()
        );
        String jwt = null;
        try {
            jwt = authClient.register(credentials);
        } catch (FeignException e) {
            handleStatus(
                    e.status(),
                    StandardCharsets.UTF_8.decode(e.responseBody().get()).toString());
        }

        repository.save(createNewUser(request));
        return jwt;
    }

    private User createNewUser(RegistrationRequest request) {
        User newUser = new User(request.getUsername(), request.getPersonalData());
        return newUser;
    }

    private void handleStatus(int status, String message) {
        switch (status) {
            case 403:
                throw new RegistrationException(message);
            default:
                throw new RegistrationException("Unsuccessful registration!");
        }
    }

}
