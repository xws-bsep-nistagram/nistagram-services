package rs.ac.uns.ftn.nistagram.user.graph.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.user.graph.domain.User;
import rs.ac.uns.ftn.nistagram.user.graph.repositories.UserRepository;

import java.util.List;

@Service
@Slf4j
public class CloseFriendsService {
    private final UserRepository userRepository;
    private final UserConstraintChecker constraintChecker;

    public CloseFriendsService(UserRepository userRepository, UserConstraintChecker constraintChecker) {
        this.userRepository = userRepository;
        this.constraintChecker = new UserConstraintChecker(userRepository);
    }


    public void addCloseFriend(String subject, String target){
        log.info("Received a close friend request from {} to {}",
                subject,
                target);

        constraintChecker.closeFriendRequestCheck(subject, target);
        userRepository.addToCloseFriends(subject, target);

        log.info("User {} is successfully added to {} close friends list ",
                subject,
                target);

    }

    public void removeCloseFriend(String subject, String target){
        log.info("Received a close friend removal request from {} to {}",
                subject,
                target);

        constraintChecker.closeFriendRemovalCheck(subject, target);
        userRepository.removeFromCloseFriends(subject, target);

        log.info("User {} is no longer in users {} close friends list", subject, target);
    }

    public List<User> findCloseFriends(String username){
        constraintChecker.userPresenceCheck(username);

        var closeFriends = userRepository.findCloseFriends(username);

        log.info("Found {} close friends for user {}", closeFriends.size(), username);

        return closeFriends;
    }

}
