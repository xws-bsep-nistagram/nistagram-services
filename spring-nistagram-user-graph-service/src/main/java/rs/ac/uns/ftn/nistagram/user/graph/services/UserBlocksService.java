package rs.ac.uns.ftn.nistagram.user.graph.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.user.graph.domain.User;
import rs.ac.uns.ftn.nistagram.user.graph.exceptions.BusinessException;
import rs.ac.uns.ftn.nistagram.user.graph.exceptions.EntityAlreadyExistsException;
import rs.ac.uns.ftn.nistagram.user.graph.exceptions.OperationNotPermittedException;
import rs.ac.uns.ftn.nistagram.user.graph.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class UserBlocksService {

    private final UserRepository userRepository;

    public List<User> findBlocked(String username){
        userPresenceCheck(username);
        if(!userRepository.hasBlockedUsers(username)){
            log.info("User {} has no blocked users", username);
            return new ArrayList<>();
        }

        List<User> blockedUsers = userRepository.findBlockedUsers(username);
        log.info("Found {} blocked users for an user {}", blockedUsers.size(), username);

        return blockedUsers;
    }

    @Transactional
    public void block(String blockerUsername, String blockingUsername){
        log.info("Received a block request from {} to {}",
                blockerUsername,
                blockingUsername);

        userPresenceCheck(blockerUsername);
        userPresenceCheck(blockingUsername);
        alreadyBlockedCheck(blockerUsername, blockingUsername);

        blockUser(blockerUsername, blockingUsername);

        log.info("User {} has blocked {}",
                blockerUsername,
                blockingUsername);
    }

    @Transactional
    public void unblock(String blockerUsername, String blockingUsername){
        log.info("Received a unblock request from {} to {}",
                blockerUsername,
                blockingUsername);

        userPresenceCheck(blockerUsername);
        userPresenceCheck(blockingUsername);
        blockedConstraintCheck(blockerUsername, blockingUsername);

        userRepository.unblock(blockerUsername,blockingUsername);

        log.info("User {} has unblocked {}",
                blockerUsername,
                blockingUsername);
    }

    private void userPresenceCheck(String username){
        if(!userRepository.existsByUsername(username)){
            var message = String.format("User %s doesn't exist", username);
            log.warn(message);
            throw new EntityAlreadyExistsException(message);
        }
    }
    private void alreadyBlockedCheck(String blockerUsername, String blockingUsername) {
        if(userRepository.hasBlocked(blockerUsername, blockingUsername)) {
            var message = String.format("User %s has already blocked user %s",
                    blockerUsername,
                    blockingUsername);
            log.warn(message);
            throw new OperationNotPermittedException(message);
        }
    }
    private void blockedConstraintCheck(String blockerUsername, String blockingUsername) {
        if(!userRepository.hasBlocked(blockerUsername, blockingUsername)) {
            var message = String.format("User %s hasn't blocked user %s",
                    blockerUsername,
                    blockingUsername);
            log.warn(message);
            throw new OperationNotPermittedException(message);
        }
    }

    private void blockUser(String blockerUsername, String blockingUsername) {
        unfollowIfFollowing(blockerUsername, blockingUsername);
        unfollowIfFollowing(blockingUsername, blockerUsername);
        var blocker = userRepository.findById(blockerUsername).get();
        var blocking = userRepository.findById(blockingUsername).get();
        blocker.block(blocking);
        userRepository.save(blocker);
    }

    private void unfollowIfFollowing(String blockerUsername, String blockingUsername) {
        if (userRepository.isFollowing(blockerUsername, blockingUsername)) {
            userRepository.unfollow(blockerUsername, blockingUsername);
            log.info("User {} is no longer following {}",
                    blockerUsername,
                    blockingUsername);
        }
    }

}
