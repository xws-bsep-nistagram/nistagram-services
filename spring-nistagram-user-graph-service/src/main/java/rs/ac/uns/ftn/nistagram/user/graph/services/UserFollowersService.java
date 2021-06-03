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
public class UserFollowersService {

    private final UserRepository userRepository;
    private final UserConstraintChecker constraintChecker;

    public UserFollowersService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.constraintChecker = new UserConstraintChecker(userRepository);
    }

    @Transactional
    public List<User> findFollowing(String username) {
        constraintChecker.userPresenceCheck(username);

        if(!userRepository.hasFollowings(username)) {
            log.info("User {} follows no one", username);
            return new ArrayList<>();
        }

        List<User> followers = userRepository.findFollowings(username);
        log.info("Found {} followings for user {}", followers.size(), username);

        return followers;
    }

    @Transactional
    public List<User> findFollowers(String username) {
        constraintChecker.userPresenceCheck(username);

        if(!userRepository.hasFollowers(username)) {
            log.info("User {} has no followers", username);
            return new ArrayList<>();
        }

        List<User> followers = userRepository.findFollowers(username);
        log.info("Found {} followers for user {}", followers.size(), username);

        return followers;
    }

    @Transactional
    public List<User> findPendingFollowers(String username){
        constraintChecker.userPresenceCheck(username);

        if(!userRepository.hasPendingFollowers(username)) {
            log.info("User {} has no pending follow requests", username);
            return new ArrayList<>();
        }

        List<User> followers = userRepository.findPendingFollowings(username);
        log.info("Found {} pending follow requests for user {}", followers.size(), username);

        return followers;
    }

    @Transactional
    public void acceptFollowRequest(String subject, String target){
        log.info("Received a follow request confirmation from {} to {}",
                subject,
                target);

        constraintChecker.pendingRequestCheck(subject, target);

        userRepository.follow(subject, target);
        userRepository.removeFollowRequest(subject, target);

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
        userRepository.removeFollowRequest(subject, target);

        log.info("User {} revoked a follow request to user {}",
                subject,
                target);
    }

    public void declineFollowRequest(String subject, String target){
        log.info("Received a follow request rejection from {} to {}",
                subject,
                target);

        constraintChecker.pendingRequestCheck(target, subject);
        userRepository.removeFollowRequest(target, subject);

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

        var targetUser = userRepository.findById(target).get();

        String message;
        if(targetUser.hasPrivateProfile()) {
            userRepository.sendFollowRequest(subject, target);
            message = String.format("User %s sent a following request to %s", subject, target);
        }
        else {
            userRepository.follow(subject, target);
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

        var subjectUser = userRepository.findById(subject).get();

        userRepository.unfollow(subjectUser.getUsername(), target);
        log.info("User {} is no longer following {}",
                subjectUser.getUsername(),
                target);

    }

}