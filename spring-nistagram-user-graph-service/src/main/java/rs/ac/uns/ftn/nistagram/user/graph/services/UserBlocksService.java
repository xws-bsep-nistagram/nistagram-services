package rs.ac.uns.ftn.nistagram.user.graph.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.user.graph.domain.User;
import rs.ac.uns.ftn.nistagram.user.graph.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserBlocksService {

    private final UserRepository userRepository;
    private final UserConstraintChecker constraintChecker;

    public UserBlocksService(UserRepository userRepository, UserConstraintChecker constraintChecker) {
        this.userRepository = userRepository;
        this.constraintChecker = new UserConstraintChecker(userRepository);
    }

    public List<User> findBlocked(String username){
        constraintChecker.userPresenceCheck(username);
        if(!userRepository.hasBlockedUsers(username)){
            log.info("User {} has no blocked users", username);
            return new ArrayList<>();
        }

        List<User> blockedUsers = userRepository.findBlockedUsers(username);
        log.info("Found {} blocked users for an user {}", blockedUsers.size(), username);

        return blockedUsers;
    }

    @Transactional
    public void block(String subject, String target){
        log.info("Received a block request from {} to {}",
                subject,
                target);

        constraintChecker.blockRequestCheck(subject, target);
        blockUser(subject, target);

        log.info("User {} has blocked {}",
                subject,
                target);
    }

    @Transactional
    public void unblock(String subject, String target){
        log.info("Received a unblock request from {} to {}",
                subject,
                target);

        constraintChecker.unblockRequestCheck(subject, target);
        userRepository.unblock(subject,target);

        log.info("User {} has unblocked {}",
                subject,
                target);
    }

    private void blockUser(String subject, String target) {
        unfollowIfFollowing(subject, target);
        unfollowIfFollowing(target, subject);
        removeFollowRequestIfPresent(subject, target);
        removeFollowRequestIfPresent(target, subject);

        userRepository.block(subject, target);
    }

    private void removeFollowRequestIfPresent(String subject, String target) {
        if(userRepository.sentFollowRequest(subject, target)) {
            userRepository.removeFollowRequest(subject, target);
            log.info("Follow request from user {} to user {} has been removed",
                    subject,
                    target);
        }
    }

    private void unfollowIfFollowing(String subject, String target) {
        if (userRepository.isFollowing(subject, target)) {
            userRepository.unfollow(subject, target);
            log.info("User {} is no longer following {}",
                    subject,
                    target);
        }
    }

}
