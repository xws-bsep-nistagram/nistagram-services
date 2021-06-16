package rs.ac.uns.ftn.nistagram.user.graph.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.user.graph.domain.User;
import rs.ac.uns.ftn.nistagram.user.graph.domain.UserStats;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.producers.UserRelationsProducer;
import rs.ac.uns.ftn.nistagram.user.graph.repositories.FollowerRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class FollowerService {

    private final FollowerRepository followerRepository;
    private final UserConstraintChecker constraintChecker;
    private final UserRelationsProducer userRelationsProducer;



    @Transactional
    public List<User> findFollowing(String username) {
        constraintChecker.userPresenceCheck(username);

        if(!followerRepository.hasFollowings(username)) {
            log.info("User {} follows no one", username);
            return new ArrayList<>();
        }

        List<User> followers = followerRepository.findFollowings(username);
        log.info("Found {} followings for user {}", followers.size(), username);

        return followers;
    }

    @Transactional
    public List<User> findFollowers(String username) {
        constraintChecker.userPresenceCheck(username);

        if(!followerRepository.hasFollowers(username)) {
            log.info("User {} has no followers", username);
            return new ArrayList<>();
        }

        List<User> followers = followerRepository.findFollowers(username);
        log.info("Found {} followers for user {}", followers.size(), username);

        return followers;
    }

    @Transactional
    public List<User> findPendingFollowers(String username){
        constraintChecker.userPresenceCheck(username);

        if(!followerRepository.hasPendingFollowers(username)) {
            log.info("User {} has no pending follow requests", username);
            return new ArrayList<>();
        }

        List<User> followers = followerRepository.findPendingFollowings(username);
        log.info("Found {} pending follow requests for user {}", followers.size(), username);

        return followers;
    }

    @Transactional
    public void acceptFollowRequest(String subject, String target){
        log.info("Received a follow request confirmation from {} to {}",
                subject,
                target);

        constraintChecker.pendingRequestCheck(subject, target);

        followerRepository.follow(subject, target);
        followerRepository.removeFollowRequest(subject, target);
        userRelationsProducer.publishUserFollowed(subject, target);

        log.info("Follow request from user {} to user {} has been accepted",
                subject,
                target);
    }

    @Transactional
    public void revokeFollowRequest(String subject, String target){
        log.info("Received a follow request cancellation from {} to {}",
                subject,
                target);

        constraintChecker.pendingRequestCheck(subject, target);
        followerRepository.removeFollowRequest(subject, target);

        log.info("User {} revoked a follow request to user {}",
                subject,
                target);
    }

    public void declineFollowRequest(String subject, String target){
        log.info("Received a follow request rejection from {} to {}",
                subject,
                target);

        constraintChecker.pendingRequestCheck(target, subject);
        followerRepository.removeFollowRequest(target, subject);

        log.info("User {} declined a follow request from user {}",
                subject,
                target);

    }

    @Transactional
    public void follow(String subject, String target){
        log.info("Received a follow request from {} to {}",
                subject,
                target);

        constraintChecker.followRequestCheck(subject, target);

        var targetUser = followerRepository.findById(target).get();

        String message;
        if(targetUser.hasPrivateProfile()) {
            followerRepository.sendFollowRequest(subject, target);
            message = String.format("User %s sent a following request to %s", subject, target);
        }
        else {
            followerRepository.follow(subject, target);
            userRelationsProducer.publishUserFollowed(subject, target);
            message = String.format("User %s follows %s", subject, target);
        }

        log.info(message);
    }
    @Transactional
    public void unfollow(String subject, String target) {
        log.info("Received a unfollow request from {} to {}",
                subject,
                target);

        constraintChecker.unfollowRequestCheck(subject,target);

        var subjectUser = followerRepository.findById(subject).get();

        followerRepository.unfollow(subjectUser.getUsername(), target);
        userRelationsProducer.publishUserUnfollowed(subject, target);
        log.info("User {} is no longer following {}",
                subjectUser.getUsername(),
                target);

    }

    public boolean checkFollowing(String subject, String target) {
        constraintChecker.userPresenceCheck(subject);
        constraintChecker.userPresenceCheck(target);
        return followerRepository.isFollowing(subject, target);

    }

    public boolean checkPending(String subject, String target) {
        constraintChecker.userPresenceCheck(subject);
        constraintChecker.userPresenceCheck(target);
        return followerRepository.sentFollowRequest(subject, target);
    }

    public UserStats getStats(String subject) {
        constraintChecker.userPresenceCheck(subject);
        Long following = followerRepository.findFollowingCount(subject);
        Long followers = followerRepository.findFollowerCount(subject);
        return new UserStats(following, followers);
    }
}
