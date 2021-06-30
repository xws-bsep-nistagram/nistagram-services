package rs.ac.uns.ftn.nistagram.user.graph.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.user.graph.controllers.payload.UserRelationshipRequest;
import rs.ac.uns.ftn.nistagram.user.graph.domain.User;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.event.userrelations.UserMutedEvent;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.event.userrelations.UserUnmutedEvent;
import rs.ac.uns.ftn.nistagram.user.graph.repositories.MutedUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class MutedUserService {

    private final MutedUserRepository mutedUserRepository;
    private final UserConstraintChecker constraintChecker;
    private final ApplicationEventPublisher publisher;

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

        publishUserMuted(subject, target);

        log.info("User {} has muted {}",
                subject,
                target);
    }

    private void publishUserMuted(String subject, String target) {

        UserMutedEvent event = new UserMutedEvent(UUID.randomUUID().toString(), new UserRelationshipRequest(subject, target));

        log.debug("Publishing a user muted event {}", event);

        publisher.publishEvent(event);

    }

    @Transactional
    public void unmute(String subject, String target) {
        log.info("Received an unmute request from {} to {}",
                subject,
                target);

        constraintChecker.unmuteRequestCheck(subject, target);

        mutedUserRepository.unmute(subject, target);

        publishUserUnmuted(subject, target);

        log.info("User {} has unmuted {}",
                subject,
                target);
    }

    private void publishUserUnmuted(String subject, String target) {

        UserUnmutedEvent event = new UserUnmutedEvent(UUID.randomUUID().toString(), new UserRelationshipRequest(subject, target));

        log.debug("Publishing a user unmuted event {}", event);

        publisher.publishEvent(event);

    }


}
