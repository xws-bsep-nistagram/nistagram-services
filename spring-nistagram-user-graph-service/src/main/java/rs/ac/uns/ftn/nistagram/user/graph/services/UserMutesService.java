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
public class UserMutesService {

    private final UserRepository userRepository;
    private final UserConstraintChecker constraintChecker;

    public UserMutesService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.constraintChecker = new UserConstraintChecker(userRepository);
    }

    @Transactional
    public List<User> findMutedUsers(String username){
        constraintChecker.userPresenceCheck(username);

        if(!userRepository.hasMutedUsers(username)) {
            log.info("User {} has no muted users", username);
            return new ArrayList<>();
        }

        List<User> mutedUsers = userRepository.findMutedUsers(username);
        log.info("Found {} muted users for user {}", mutedUsers.size(), username);

        return mutedUsers;

    }

    @Transactional
    public void mute(String subject, String target) {
        log.info("Received a mute request from {} to {}",
                subject,
                target);

        constraintChecker.muteRequestCheck(subject, target);

        userRepository.mute(subject, target);

        log.info("User {} has muted {}",
                subject,
                target);
    }

    @Transactional
    public void unmute(String subject, String target) {
        log.info("Received an unmute request from {} to {}",
                subject,
                target);

        constraintChecker.unmuteRequestCheck(subject, target);

        userRepository.unmute(subject, target);

        log.info("User {} has unmuted {}",
                subject,
                target);
    }

}
