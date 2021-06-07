package rs.ac.uns.ftn.nistagram.user.graph.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.user.graph.domain.User;
import rs.ac.uns.ftn.nistagram.user.graph.repositories.BlockedUserRepository;
import rs.ac.uns.ftn.nistagram.user.graph.repositories.FollowerRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class BlockedUserService {

    private final BlockedUserRepository blockedUserRepository;
    private final UserConstraintChecker constraintChecker;
    private final FollowerRepository followerRepository;

    public List<User> findBlocked(String username){
        constraintChecker.userPresenceCheck(username);
        if(!blockedUserRepository.hasBlockedUsers(username)){
            log.info("User {} has no blocked users", username);
            return new ArrayList<>();
        }

        List<User> blockedUsers = blockedUserRepository.findBlockedUsers(username);
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
        blockedUserRepository.unblock(subject,target);

        log.info("User {} has unblocked {}",
                subject,
                target);
    }

    private void blockUser(String subject, String target) {
        unfollowIfFollowing(subject, target);
        unfollowIfFollowing(target, subject);
        removeFollowRequestIfPresent(subject, target);
        removeFollowRequestIfPresent(target, subject);

        blockedUserRepository.block(subject, target);
    }

    private void removeFollowRequestIfPresent(String subject, String target) {
        if(followerRepository.sentFollowRequest(subject, target)) {
            followerRepository.removeFollowRequest(subject, target);
            log.info("Follow request from user {} to user {} has been removed",
                    subject,
                    target);
        }
    }

    private void unfollowIfFollowing(String subject, String target) {
        if (followerRepository.isFollowing(subject, target)) {
            followerRepository.unfollow(subject, target);
            log.info("User {} is no longer following {}",
                    subject,
                    target);
        }
    }

}
