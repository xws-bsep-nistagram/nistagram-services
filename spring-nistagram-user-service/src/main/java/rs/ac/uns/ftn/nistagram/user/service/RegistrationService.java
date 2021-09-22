package rs.ac.uns.ftn.nistagram.user.service;

import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.user.domain.user.RegistrationRequest;
import rs.ac.uns.ftn.nistagram.user.domain.user.User;
import rs.ac.uns.ftn.nistagram.user.http.auth.AuthClient;
import rs.ac.uns.ftn.nistagram.user.http.auth.Credentials;
import rs.ac.uns.ftn.nistagram.user.infrastructure.exceptions.RegistrationException;
import rs.ac.uns.ftn.nistagram.user.messaging.event.UserCreatedEvent;
import rs.ac.uns.ftn.nistagram.user.messaging.mappers.UserEventPayloadMapper;
import rs.ac.uns.ftn.nistagram.user.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class RegistrationService {

    private final AuthClient authClient;
    private final UserRepository repository;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public void register(RegistrationRequest request) {

        Credentials credentials = new Credentials(request);

        sendRegistrationRequest(credentials);

        User created = repository.save(createNewUser(request));

        log.info("Received a registration request for an user profile with an username '{}'",
                created.getUsername());

        publishRegisteredUser(created);

    }

    public void sendRegistrationRequest(Credentials credentials) {
        try {
            log.info("Sending a registration request from user-service to auth-service for an user profile with an username '{}'",
                    credentials.getUsername());
            authClient.register(credentials);
        } catch (FeignException e) {
            if (e.status() == 403 && e.responseBody().isPresent()) {
                String message = StandardCharsets.UTF_8.decode(e.responseBody().get()).toString();
                throw new RegistrationException(message);
            } else {
                throw new RegistrationException("Unsuccessful registration!");
            }
        }
    }

    private void publishRegisteredUser(User user) {

        UserCreatedEvent event = new UserCreatedEvent(UUID.randomUUID().toString(),
                UserEventPayloadMapper.toPayload(user));

        publisher.publishEvent(event);
    }


    private User createNewUser(RegistrationRequest request) {
        return new User(request.getUsername(), request.getPersonalData());
    }

}
