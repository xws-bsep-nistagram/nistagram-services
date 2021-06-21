package rs.ac.uns.ftn.nistagram.user.graph.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.user.graph.domain.User;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.producers.UserRelationsProducer;
import rs.ac.uns.ftn.nistagram.user.graph.repositories.MutedUserRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MutedUserService {

    private final MutedUserRepository mutedUserRepository;
    private final UserConstraintChecker constraintChecker;
    private final UserRelationsProducer userRelationsProducer;

    @Transactional
    public List<User> findMutedUsers(String username) {
        constraintChecker.userPresenceCheck(username);

        if (!mutedUserRepository.hasMutedUsers(username)) {
            log.info("User {} has no muted users", username);
            return new ArrayList<>();
        }

        List<User> mutedUsers = mutedUserRepository.findMutedUsers(username);
        log.info("Found {} muted users for user {}", mutedUsers.size(), username);

        return mutedUsers;

    }

    public Boolean hasMuted(String subject, String target) {
        log.info("Checking if {} has muted {}", subject, target);
        return mutedUserRepository.hasMuted(subject, target);
    }

    @Transactional
    public void mute(String subject, String target) {
        log.info("Received a mute request from {} to {}",
                subject,
                target);

        constraintChecker.muteRequestCheck(subject, target);

        mutedUserRepository.mute(subject, target);

        userRelationsProducer.publishUserMuted(subject, target);

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

        mutedUserRepository.unmute(subject, target);

        userRelationsProducer.publishUserUnmuted(subject, target);

        log.info("User {} has unmuted {}",
                subject,
                target);
    }


}
