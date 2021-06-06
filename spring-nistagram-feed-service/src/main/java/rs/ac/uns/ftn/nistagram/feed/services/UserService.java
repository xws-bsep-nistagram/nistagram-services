package rs.ac.uns.ftn.nistagram.feed.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.feed.domain.user.User;
import rs.ac.uns.ftn.nistagram.feed.exceptions.EntityAlreadyExistsException;
import rs.ac.uns.ftn.nistagram.feed.exceptions.EntityNotFoundException;
import rs.ac.uns.ftn.nistagram.feed.repositories.UserRepository;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void create(User user){
        userPresenceCheck(user);
        log.info("User creation request for a user {} received", user.getUsername());
        userRepository.save(user);
        log.info("User {} has been successfully created", user.getUsername());
    }

    private void userPresenceCheck(User user) {
        if(userRepository.existsById(user.getUsername())) {
            var message = String.format("User %s already exist", user.getUsername());
            log.warn(message);
            throw new EntityAlreadyExistsException(message);
        }
    }
    private void userAbscenceCheck(User user) {
        if(!userRepository.existsById(user.getUsername())) {
            var message = String.format("User %s doesn't exist", user.getUsername());
            log.warn(message);
            throw new EntityNotFoundException(message);
        }
    }
}
