package rs.ac.uns.ftn.nistagram.user.service;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.user.domain.user.RegistrationRequest;
import rs.ac.uns.ftn.nistagram.user.domain.user.User;
import rs.ac.uns.ftn.nistagram.user.http.auth.AuthClient;
import rs.ac.uns.ftn.nistagram.user.http.auth.Credentials;
import rs.ac.uns.ftn.nistagram.user.infrastructure.exceptions.RegistrationException;
import rs.ac.uns.ftn.nistagram.user.messaging.producers.UserProducer;
import rs.ac.uns.ftn.nistagram.user.repository.UserRepository;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class RegistrationService {

    private final AuthClient authClient;
    private final UserRepository repository;
    private final UserProducer producer;

    public RegistrationService(AuthClient authClient,
                               UserRepository repository,
                               UserProducer producer) {
        this.authClient = authClient;
        this.repository = repository;
        this.producer = producer;
    }

    @Transactional
    public String register(RegistrationRequest request) {
        Credentials credentials = new Credentials(
                request.getUsername(),
                request.getPassword(),
                request.getEmail()
        );

        User created = repository.save(createNewUser(request));
        log.debug("Created user new user profile for username '{}'", created.getUsername());
        String jwt = sendRegistrationRequest(credentials);
        producer.publishUserCreated(created);
        return jwt;
    }

    private String sendRegistrationRequest(Credentials credentials) {
        try {
            log.info("Sending registration request from user-service to auth-service for username '{}'", credentials.getUsername());
            return authClient.register(credentials);
        } catch (FeignException e) {
            if (e.status() == 403) {
                String message = StandardCharsets.UTF_8.decode(e.responseBody().get()).toString();
                throw new RegistrationException(message);
            } else {
                throw new RegistrationException("Unsuccessful registration!");
            }
        }
    }

    private User createNewUser(RegistrationRequest request) {
        return new User(request.getUsername(), request.getPersonalData());
    }

}
