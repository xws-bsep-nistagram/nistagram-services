package rs.ac.uns.ftn.nistagram.user.graph.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.user.graph.domain.User;
import rs.ac.uns.ftn.nistagram.user.graph.exceptions.EntityAlreadyExistsException;
import rs.ac.uns.ftn.nistagram.user.graph.exceptions.OperationNotPermittedException;
import rs.ac.uns.ftn.nistagram.user.graph.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class UserMutesService {

    private final UserRepository userRepository;

    public List<User> findMutedUsers(String username){
        userPresenceCheck(username);

        if(!userRepository.hasMutedUsers(username)) {
            log.info("User {} has no muted users", username);
            return new ArrayList<>();
        }

        List<User> mutedUsers = userRepository.findMutedUsers(username);
        log.info("Found {} muted users for user {}", mutedUsers.size(), username);

        return mutedUsers;

    }

    public void mute(String subject, String target) {
        log.info("Received a mute request from {} to {}",
                subject,
                target);

        userPresenceCheck(subject);
        userPresenceCheck(target);
        followingConstraintCheck(subject, target);
        blockedConstraintCheck(subject, target);
        alreadyMutedConstraintCheck(subject, target);

        var subjectUser = userRepository.findById(subject).get();
        var targetUser = userRepository.findById(target).get();

        subjectUser.addMuted(targetUser);
        userRepository.save(subjectUser);
        log.info("User {} has muted {}",
                subject,
                target);
    }

    public void unmute(String subject, String target) {
        log.info("Received an unmute request from {} to {}",
                subject,
                target);

        userPresenceCheck(subject);
        userPresenceCheck(target);
        mutedConstraintCheck(subject, target);

        var subjectUser = userRepository.findById(subject).get();
        var targetUser = userRepository.findById(target).get();

        subjectUser.addMuted(targetUser);
        userRepository.save(subjectUser);
        log.info("User {} has muted {}",
                subject,
                target);
    }


    private void alreadyMutedConstraintCheck(String subject, String target) {
        if(userRepository.mutedUser(subject, target)) {
            var message = String.format("User %s has already muted user %s",
                    subject,
                    target);
            log.warn(message);
            throw new OperationNotPermittedException(message);
        }
    }

    private void mutedConstraintCheck(String subject, String target) {
        if(!userRepository.hasMuted(subject, target)) {
            var message = String.format("User %s hasn't muted user %s",
                    subject,
                    target);
            log.warn(message);
            throw new OperationNotPermittedException(message);
        }
    }

    private void userPresenceCheck(String username){
        if(!userRepository.existsByUsername(username)){
            var message = String.format("User %s doesn't exist", username);
            log.warn(message);
            throw new EntityAlreadyExistsException(message);
        }
    }

    private void followingConstraintCheck(String subject, String target) {
        if(!userRepository.isFollowing(subject, target)) {
            var message = String.format("User %s doesn't follow user %s",
                    subject,
                    target);
            log.warn(message);
            throw new OperationNotPermittedException(message);
        }
    }
    private void blockedConstraintCheck(String subject, String target) {
        if(userRepository.hasBlocked(subject, target)) {
            var message = String.format("User %s is blocked by user %s",
                    subject,
                    target);
            log.warn(message);
            throw new OperationNotPermittedException(message);
        }
    }
}
