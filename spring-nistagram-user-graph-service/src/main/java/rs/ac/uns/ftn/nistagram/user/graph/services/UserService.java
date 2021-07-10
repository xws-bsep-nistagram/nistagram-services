package rs.ac.uns.ftn.nistagram.user.graph.services;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.user.graph.domain.User;
import rs.ac.uns.ftn.nistagram.user.graph.exceptions.EntityAlreadyExistsException;
import rs.ac.uns.ftn.nistagram.user.graph.exceptions.EntityNotFoundException;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.util.TransactionIdHolder;
import rs.ac.uns.ftn.nistagram.user.graph.repositories.UserRepository;

@AllArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TransactionIdHolder transactionIdHolder;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public void create(User user) {
        log.info("Received a user creation request for a user {}", user.getUsername());
        userUniquenessCheck(user);
        User createdUser = userRepository.save(user);
        log.info("User {} has been successfully created", createdUser.getUsername());
    }

    @Transactional
    public void update(User user) {
        log.info("Received a user update request for a user {}", user.getUsername());
        userPresenceCheck(user);
        User updatedUser = userRepository.update(user.getUsername(), user.getProfileType());
        log.info("User {} has been successfully updated", updatedUser.getUsername());
    }

    @Transactional
    public void delete(User user) {
        log.info("Received a user delete request for a user {}", user.getUsername());
        userPresenceCheck(user);
        userRepository.detachDelete(user.getUsername());
        log.info("User {} has been successfully deleted", user.getUsername());
    }

    @Transactional
    public void delete(String username) {
        log.info("Received a user delete request for a user {}", username);

        userRepository.detachDelete(username);

        log.info("User {} has been successfully deleted", username);

    }

    private void userUniquenessCheck(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            var message = String.format("User %s already exists", user.getUsername());
            log.warn(message);
            throw new EntityAlreadyExistsException(message);
        }
    }

    private void userPresenceCheck(User user) {
        if (!userRepository.existsByUsername(user.getUsername())) {
            var message = String.format("User %s doesn't exist", user.getUsername());
            log.warn(message);
            throw new EntityNotFoundException(message);
        }
    }

}
