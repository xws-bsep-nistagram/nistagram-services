package rs.ac.uns.ftn.nistagram.user.graph.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.user.graph.domain.User;
import rs.ac.uns.ftn.nistagram.user.graph.exceptions.EntityAlreadyExistsException;
import rs.ac.uns.ftn.nistagram.user.graph.exceptions.OperationNotPermittedException;
import rs.ac.uns.ftn.nistagram.user.graph.repositories.*;

@Service
@Slf4j
@AllArgsConstructor
public class UserConstraintChecker {
    private final UserRepository userRepository;
    private final BlockedUserRepository blockedUserRepository;
    private final MutedUserRepository mutedUserRepository;
    private final CloseFriendRepository closeFriendRepository;
    private final FollowerRepository followerRepository;

    public void pendingRequestCheck(String subject, String target) {
        basicRelationCheck(subject, target);
        followRequestExists(subject, target);
    }

    public void followRequestCheck(String subject, String target) {
        basicRelationCheck(subject, target);
        blockedConstraintCheck(subject, target);
        alreadyFollowsCheck(subject, target);
        followRequestPresenceCheck(subject, target);
    }


    public void unfollowRequestCheck(String subject, String target) {
        basicRelationCheck(subject, target);
        followingConstraintCheck(subject, target);
    }

    public void muteRequestCheck(String subject, String target) {
        basicRelationCheck(subject, target);
        followingConstraintCheck(subject, target);
        blockedConstraintCheck(subject, target);
        alreadyMutedConstraintCheck(subject, target);
    }

    public void unmuteRequestCheck(String subject, String target) {
        basicRelationCheck(subject, target);
        mutedConstraintCheck(subject, target);
    }

    public void blockRequestCheck(String subject, String target) {
        basicRelationCheck(subject, target);
        alreadyBlockedCheck(subject, target);
    }

    public void unblockRequestCheck(String subject, String target) {
        basicRelationCheck(subject, target);
        unblockConstraintCheck(subject, target);
    }

    public void closeFriendRequestCheck(String subject, String target) {
        basicRelationCheck(subject, target);
        blockedConstraintCheck(subject, target);
        followingConstraintCheck(subject, target);
        alreadyCloseFriendCheck(subject, target);
    }

    public void basicRelationCheck(String subject, String target) {
        userPresenceCheck(subject);
        userPresenceCheck(target);
        userBannedCheck(subject);
        userBannedCheck(target);
    }

    public void userBannedCheck(String username) {
        User found = userRepository.findById(username).get();
        if(found.isBanned()){
            var message = String.format("User with an username '%s' is banned", username);
            log.warn(message);
            throw new OperationNotPermittedException(message);
        }
    }

    public void closeFriendRemovalCheck(String subject, String target) {
        userPresenceCheck(subject);
        userPresenceCheck(target);
        closeFriendConstraintCheck(subject, target);
    }

    private void closeFriendConstraintCheck(String subject, String target) {
        if (!closeFriendRepository.isCloseFriend(subject, target)) {
            var message = String.format("User %s isn't in users %s close friends list",
                    subject, target);
            log.warn(message);
            throw new OperationNotPermittedException(message);
        }
    }

    private void alreadyCloseFriendCheck(String subject, String target) {
        if (closeFriendRepository.isCloseFriend(subject, target)) {
            var message = String.format("User %s is already in users %s close friends list",
                    subject, target);
            log.warn(message);
            throw new OperationNotPermittedException(message);
        }
    }

    public void userPresenceCheck(String username) {
        if (!userRepository.existsByUsername(username)) {
            var message = String.format("User %s doesn't exist", username);
            log.warn(message);
            throw new EntityAlreadyExistsException(message);
        }
    }

    private void followRequestExists(String subject, String target) {
        if (!followerRepository.sentFollowRequest(subject, target)) {
            var message = String.format("Follow request from %s to %s doesn't exist",
                    subject,
                    target);
            log.warn(message);
            throw new OperationNotPermittedException(message);
        }
    }

    private void followRequestPresenceCheck(String subject, String target) {
        if (followerRepository.sentFollowRequest(subject, target)) {
            var message = String.format("User %s is waiting for %s to accept his follow request",
                    subject,
                    target);
            log.warn(message);
            throw new OperationNotPermittedException(message);
        }
    }

    private void unblockConstraintCheck(String subject, String target) {
        if (!blockedUserRepository.hasBlocked(subject, target)) {
            var message = String.format("User %s isn't blocked by user %s",
                    target,
                    subject);
            log.warn(message);
            throw new OperationNotPermittedException(message);
        }
    }

    private void blockedConstraintCheck(String subject, String target) {
        if (blockedUserRepository.hasBlocked(target, subject)) {
            var message = String.format("User %s is blocked by user %s",
                    subject,
                    target);
            log.warn(message);
            throw new OperationNotPermittedException(message);
        }
    }

    private void alreadyBlockedCheck(String subject, String target) {
        if (blockedUserRepository.hasBlocked(subject, target)) {
            var message = String.format("User %s has already blocked user %s",
                    subject,
                    target);
            log.warn(message);
            throw new OperationNotPermittedException(message);
        }
    }

    private void alreadyFollowsCheck(String subject, String target) {
        if (followerRepository.isFollowing(subject, target)) {
            var message = String.format("User %s already follows user %s",
                    subject,
                    target);
            log.warn(message);
            throw new OperationNotPermittedException(message);
        }
    }

    private void mutedConstraintCheck(String subject, String target) {
        if (!mutedUserRepository.hasMuted(subject, target)) {
            var message = String.format("User %s hasn't muted user %s",
                    subject,
                    target);
            log.warn(message);
            throw new OperationNotPermittedException(message);
        }
    }

    private void alreadyMutedConstraintCheck(String subject, String target) {
        if (mutedUserRepository.mutedUser(subject, target)) {
            var message = String.format("User %s has already muted user %s",
                    subject,
                    target);
            log.warn(message);
            throw new OperationNotPermittedException(message);
        }
    }

    private void followingConstraintCheck(String subject, String target) {
        if (!followerRepository.isFollowing(subject, target)) {
            var message = String.format("User %s doesn't follow user %s",
                    subject,
                    target);
            log.warn(message);
            throw new OperationNotPermittedException(message);
        }
    }

}
