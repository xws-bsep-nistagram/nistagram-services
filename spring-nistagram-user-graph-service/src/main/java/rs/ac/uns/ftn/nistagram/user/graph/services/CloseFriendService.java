package rs.ac.uns.ftn.nistagram.user.graph.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.user.graph.domain.User;
import rs.ac.uns.ftn.nistagram.user.graph.repositories.CloseFriendRepository;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CloseFriendService {

    private final CloseFriendRepository closeFriendRepository;
    private final UserConstraintChecker constraintChecker;

    public void addCloseFriend(String subject, String target){
        log.info("Received a close friend request from {} to {}",
                subject,
                target);

        constraintChecker.closeFriendRequestCheck(subject, target);
        closeFriendRepository.addToCloseFriends(subject, target);

        log.info("User {} is successfully added to {} close friends list ",
                subject,
                target);
    }

    public void removeCloseFriend(String subject, String target){
        log.info("Received a close friend removal request from {} to {}",
                subject,
                target);

        constraintChecker.closeFriendRemovalCheck(subject, target);
        closeFriendRepository.removeFromCloseFriends(subject, target);

        log.info("User {} is no longer in users {} close friends list", subject, target);
    }

    public List<User> findCloseFriends(String username){
        constraintChecker.userPresenceCheck(username);

        var closeFriends = closeFriendRepository.findCloseFriends(username);

        log.info("Found {} close friends for user {}", closeFriends.size(), username);

        return closeFriends;
    }

    public boolean checkIfCloseFriends(String subject, String target) {
        constraintChecker.userPresenceCheck(subject);
        constraintChecker.userPresenceCheck(target);
        return closeFriendRepository.isCloseFriend(subject, target);
    }
}
