package rs.ac.uns.ftn.nistagram.feed.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.PostFeedEntry;
import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.StoryFeedEntry;
import rs.ac.uns.ftn.nistagram.feed.domain.user.User;
import rs.ac.uns.ftn.nistagram.feed.exceptions.EntityAlreadyExistsException;
import rs.ac.uns.ftn.nistagram.feed.exceptions.EntityNotFoundException;
import rs.ac.uns.ftn.nistagram.feed.messaging.event.user.RegistrationSucceededEvent;
import rs.ac.uns.ftn.nistagram.feed.messaging.util.TransactionIdHolder;
import rs.ac.uns.ftn.nistagram.feed.repositories.PostFeedRepository;
import rs.ac.uns.ftn.nistagram.feed.repositories.StoryFeedRepository;
import rs.ac.uns.ftn.nistagram.feed.repositories.UserRepository;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ApplicationEventPublisher publisher;
    private final TransactionIdHolder transactionIdHolder;

    @Transactional
    public void create(User user) {
        userPresenceCheck(user.getUsername());
        log.info("User creation request for an user '{}' received", user.getUsername());
        userRepository.save(user);
        publishRegistrationSucceeded(user);
        log.info("User '{}' has been successfully created", user.getUsername());
    }

    private void publishRegistrationSucceeded(User user) {

        RegistrationSucceededEvent event = new RegistrationSucceededEvent(transactionIdHolder.getCurrentTransactionId(), user.getUsername());

        log.info("Publishing a registration succeeded event {}", event);

        publisher.publishEvent(event);

    }


    @Transactional
    public void ban(String username) {
        log.info("Received an user ban request for for an user with an username '{}'", username);

        userAbsenceCheck(username);

        User found = userRepository.getOne(username);
        found.ban();
        userRepository.save(found);

        log.info("User with an username '{}' has been successfully banned", username);
    }

    @Transactional
    public void unban(String username) {

        log.info("Received an user unban request for for an user with an username '{}'", username);

        userAbsenceCheck(username);

        User found = userRepository.getOne(username);
        found.unban();
        userRepository.save(found);

        log.info("User with an username '{}' has been successfully unbanned", username);

    }


    private void userPresenceCheck(String username) {
        if (userRepository.existsById(username)) {
            var message = String.format("User '%s' already exist",username);
            log.warn(message);
            throw new EntityAlreadyExistsException(message);
        }
    }


    private void userAbsenceCheck(String username) {
        if (!userRepository.existsById(username)) {
            var message = String.format("User '%s' doesn't exist", username);
            log.warn(message);
            throw new EntityNotFoundException(message);
        }
    }

}
