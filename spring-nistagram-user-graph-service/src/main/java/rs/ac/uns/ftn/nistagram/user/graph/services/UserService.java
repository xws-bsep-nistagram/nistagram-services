package rs.ac.uns.ftn.nistagram.user.graph.services;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.user.graph.domain.User;
import rs.ac.uns.ftn.nistagram.user.graph.exceptions.EntityAlreadyExistsException;
import rs.ac.uns.ftn.nistagram.user.graph.exceptions.EntityNotFoundException;
import rs.ac.uns.ftn.nistagram.user.graph.exceptions.OperationNotPermittedException;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.util.TransactionIdHolder;
import rs.ac.uns.ftn.nistagram.user.graph.repositories.UserRepository;

@AllArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void create(User user) {
        log.info("Received a user creation request for an user with an username '{}'", user.getUsername());
        userUniquenessCheck(user.getUsername());
        User createdUser = userRepository.save(user);
        log.info("User with an username '{}' has been successfully registered", createdUser.getUsername());
    }

    @Transactional
    public void update(User user) {
        log.info("Received a user update request for an user with an username '{}'", user.getUsername());
        userPresenceCheck(user.getUsername());
        User updatedUser = userRepository.update(user.getUsername(), user.getProfileType());
        log.info("User {} has been successfully updated", updatedUser.getUsername());
    }

    @Transactional
    public void delete(String username) {
        log.info("Received an user delete request for an user with an username '{}'", username);
        userPresenceCheck(username);
        userRepository.detachDelete(username);
        log.info("User with an username '{}' has been successfully deleted", username);
    }

    @Transactional
    public void ban(String username) {
        log.info("Received an user ban request for for an user with an username '{}'", username);
        userPresenceCheck(username);
        userAlreadyBannedCheck(username);
        userRepository.ban(username);
        log.info("User with an username '{}' has been successfully banned", username);
    }

    @Transactional
    public void unban(String username) {
        log.info("Received an user unban request for for an user with an username '{}'", username);
        userPresenceCheck(username);
        userRepository.unban(username);
        log.info("User with an username '{}' has been successfully unbanned", username);
    }

    private void userAlreadyBannedCheck(String username) {
        User found = userRepository.findById(username).get();
        if(found.isBanned()){
            var message = String.format("User with an username '%s' is already banned", username);
            log.warn(message);
            throw new OperationNotPermittedException(message);
        }
    }

    private void userUniquenessCheck(String username) {
        if (userRepository.existsByUsername(username)) {
            var message = String.format("User %s already exists", username);
            log.warn(message);
            throw new EntityAlreadyExistsException(message);
        }
    }

    private void userPresenceCheck(String username) {
        if (!userRepository.existsByUsername(username)) {
            var message = String.format("User %s doesn't exist", username);
            log.warn(message);
            throw new EntityNotFoundException(message);
        }
    }

}
