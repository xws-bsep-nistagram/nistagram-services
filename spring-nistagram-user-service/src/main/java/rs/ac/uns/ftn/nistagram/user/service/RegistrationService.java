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

        repository.save(createNewUser(request));

        return sendRegistrationRequest(credentials);
    }

    private String sendRegistrationRequest(Credentials credentials) {
        try {
            return authClient.register(credentials);
        } catch (FeignException e) {
            if (e.status() == 403) {
                String message = StandardCharsets.UTF_8.decode(e.responseBody().get()).toString();
                throw new RegistrationException(message);
            }
            throw new RegistrationException("Unsuccessful registration!");
        }
    }

    private User createNewUser(RegistrationRequest request) {
        return new User(request.getUsername(), request.getPersonalData());
    }

}
