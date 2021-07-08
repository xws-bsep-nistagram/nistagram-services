package rs.ac.uns.ftn.nistagram.user.graph.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.user.graph.controllers.payload.UserRelationshipRequest;
import rs.ac.uns.ftn.nistagram.user.graph.domain.Recommendation;
import rs.ac.uns.ftn.nistagram.user.graph.domain.User;
import rs.ac.uns.ftn.nistagram.user.graph.domain.UserStats;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.event.notifications.FollowAcceptedEvent;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.event.notifications.FollowRequestedEvent;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.event.notifications.NewFollowEvent;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.event.userrelations.UserFollowedEvent;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.event.userrelations.UserUnfollowedEvent;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.mappers.EventPayloadMapper;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.payload.notification.NotificationType;
import rs.ac.uns.ftn.nistagram.user.graph.repositories.FollowerRepository;
import rs.ac.uns.ftn.nistagram.user.graph.repositories.MutedUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class FollowerService {

    private final FollowerRepository followerRepository;
    private final MutedUserRepository mutedUserRepository;
    private final UserConstraintChecker constraintChecker;
    private final ApplicationEventPublisher publisher;


    @Transactional
    public List<User> findFollowing(String username) {
        constraintChecker.userPresenceCheck(username);

        if (!followerRepository.hasFollowings(username)) {
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

        if (!followerRepository.hasFollowers(username)) {
            log.info("User {} has no followers", username);
            return new ArrayList<>();
        }

        List<User> followers = followerRepository.findFollowers(username);
        log.info("Found {} followers for user {}", followers.size(), username);

        return followers;
    }

    @Transactional
    public List<User> findPendingFollowers(String username) {
        constraintChecker.userPresenceCheck(username);

        if (!followerRepository.hasPendingFollowers(username)) {
            log.info("User {} has no pending follow requests", username);
            return new ArrayList<>();
        }

        List<User> followers = followerRepository.findPendingFollowings(username);
        log.info("Found {} pending follow requests for user {}", followers.size(), username);

        return followers;
    }

    @Transactional
    public void acceptFollowRequest(String subject, String target) {
        log.info("Received a follow request confirmation from {} to {}",
                subject,
                target);

        constraintChecker.pendingRequestCheck(subject, target);

        followerRepository.follow(subject, target);
        followerRepository.removeFollowRequest(subject, target);
        publishUserFollowed(subject, target);
        publishFollowAccepted(subject, target);

        log.info("Follow request from user {} to user {} has been accepted",
                subject,
                target);
    }

    @Transactional
    public List<Recommendation> recommend(String username) {
        constraintChecker.userPresenceCheck(username);

        log.info("Finding follow recommendations for an user :'{}'", username);

        if (!followerRepository.hasFollowings(username))
            return findMostPopular(username);
        else
            return findRecommendations(username);

    }

    private List<Recommendation> findRecommendations(String username) {
        List<Recommendation> recommendations = new ArrayList<>();

        List<User> recommendedUsers = followerRepository.recommend(username);
        log.info("Found {} recommendations for an user :'{}'", recommendedUsers.size(), username);

        if (recommendedUsers.isEmpty())
            return findMostPopular(username);

        recommendedUsers.forEach(user -> {
            List<User> mutualConnections = followerRepository
                    .findMutualConnection(username, user.getUsername());
            recommendations.add(new Recommendation(user, mutualConnections));
        });

        return recommendations;
    }

    private List<Recommendation> findMostPopular(String username) {
        List<Recommendation> recommendations = new ArrayList<>();

        log.info("User {} follows no one, returning users ordered by popularity", username);

        List<User> mostPopularUsers = followerRepository.findAllOrderedByPopularity(username);
        mostPopularUsers.forEach(user -> recommendations.add(new Recommendation(user)));

        return recommendations;
    }

    private void publishUserFollowed(String subject, String target) {

        UserFollowedEvent event = new UserFollowedEvent(UUID.randomUUID().toString(), new UserRelationshipRequest(subject, target));

        log.info("Publishing a user followed event {}", event);

        publisher.publishEvent(event);

    }

    private void publishFollowAccepted(String subject, String target) {

        FollowAcceptedEvent event = new FollowAcceptedEvent(UUID.randomUUID().toString(),
                EventPayloadMapper.toDomain(target, subject, NotificationType.FOLLOW_REQUEST_ACCEPTED));

        log.info("Publishing a follow accepted event {}", event);

        publisher.publishEvent(event);

    }

    @Transactional
    public void revokeFollowRequest(String subject, String target) {
        log.info("Received a follow request cancellation from {} to {}",
                subject,
                target);

        constraintChecker.pendingRequestCheck(subject, target);
        followerRepository.removeFollowRequest(subject, target);

        log.info("User {} revoked a follow request to user {}",
                subject,
                target);
    }

    public void declineFollowRequest(String subject, String target) {
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
    public void follow(String subject, String target) {
        log.info("Received a follow request from {} to {}",
                subject,
                target);

        constraintChecker.followRequestCheck(subject, target);

        var targetUser = followerRepository.findById(target).get();

        String message;
        if (targetUser.hasPrivateProfile()) {
            followerRepository.sendFollowRequest(subject, target);
            publishFollowRequested(subject, target);
            message = String.format("User %s sent a following request to %s", subject, target);

        } else {
            followerRepository.follow(subject, target);
            publishUserFollowed(subject, target);
            publishNewFollow(subject, target);
            message = String.format("User %s follows %s", subject, target);
        }

        log.info(message);
    }

    private void publishFollowRequested(String subject, String target) {

        FollowRequestedEvent event = new FollowRequestedEvent(UUID.randomUUID().toString(),
                EventPayloadMapper.toDomain(subject, target, NotificationType.NEW_FOLLOW_REQUEST));

        log.info("Publishing a follow requested event {}", event);

        publisher.publishEvent(event);

    }

    private void publishNewFollow(String subject, String target) {

        NewFollowEvent event = new NewFollowEvent(UUID.randomUUID().toString(),
                EventPayloadMapper.toDomain(subject, target, NotificationType.NEW_FOLLOWER));

        log.info("Publishing a new follow event {}", event);

        publisher.publishEvent(event);

    }


    @Transactional
    public void unfollow(String subject, String target) {
        log.info("Received a unfollow request from {} to {}",
                subject,
                target);

        constraintChecker.unfollowRequestCheck(subject, target);

        var subjectUser = followerRepository.findById(subject).get();

        followerRepository.unfollow(subjectUser.getUsername(), target);

        if (mutedUserRepository.hasMuted(subject, target))
            mutedUserRepository.unmute(subject, target);

        publishUserUnfollowed(subject, target);

        log.info("User {} is no longer following {}",
                subjectUser.getUsername(),
                target);

    }

    private void publishUserUnfollowed(String subject, String target) {

        UserUnfollowedEvent event = new UserUnfollowedEvent(UUID.randomUUID().toString(), new UserRelationshipRequest(subject, target));

        log.info("Publishing a user unfollowed event {}", event);

        publisher.publishEvent(event);

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
