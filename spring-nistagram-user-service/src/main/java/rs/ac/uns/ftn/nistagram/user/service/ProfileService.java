package rs.ac.uns.ftn.nistagram.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.user.domain.user.PersonalData;
import rs.ac.uns.ftn.nistagram.user.domain.user.PrivacyData;
import rs.ac.uns.ftn.nistagram.user.domain.user.User;
import rs.ac.uns.ftn.nistagram.user.domain.user.UserStats;
import rs.ac.uns.ftn.nistagram.user.domain.user.preferences.NotificationPreferences;
import rs.ac.uns.ftn.nistagram.user.http.graph.FollowerStats;
import rs.ac.uns.ftn.nistagram.user.http.graph.UserGraphClient;
import rs.ac.uns.ftn.nistagram.user.http.mapper.UserStatsMapper;
import rs.ac.uns.ftn.nistagram.user.http.post.PostClient;
import rs.ac.uns.ftn.nistagram.user.http.post.PostStats;
import rs.ac.uns.ftn.nistagram.user.infrastructure.exceptions.BannedException;
import rs.ac.uns.ftn.nistagram.user.infrastructure.exceptions.EntityNotFoundException;
import rs.ac.uns.ftn.nistagram.user.messaging.event.UserUpdatedEvent;
import rs.ac.uns.ftn.nistagram.user.messaging.mappers.UserEventPayloadMapper;
import rs.ac.uns.ftn.nistagram.user.repository.UserRepository;
import rs.ac.uns.ftn.nistagram.user.repository.specification.UserSpecification;
import rs.ac.uns.ftn.nistagram.user.sagas.UserSagaOrchestrator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ProfileService {

    private final UserRepository repository;
    private final ApplicationEventPublisher publisher;
    private final UserGraphClient userGraphClient;
    private final PostClient postClient;
    private final UserStatsMapper statsMapper;
    private final UserSagaOrchestrator userSagaOrchestrator;


    @Transactional(readOnly = true)
    public User get(String username) {
        log.info("Getting user by username: '{}'", username);

        User found = repository.findById(username).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("Profile for username '%s' doesn't exist!", username)
                )
        );
        bannedCheck(found);
        return found;
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        List<User> users = repository
                .findAll()
                .stream()
                .filter(user -> !user.isBanned())
                .collect(Collectors.toList());
        log.info("Found {} registered users", users.size());
        return users;
    }


    @Transactional
    public User ban(String username) {
        User found = get(username);

        bannedCheck(found);

        userSagaOrchestrator.executeBanSaga(found);

        return found;
    }

    @Transactional
    public User delete(String username) {
        User found = get(username);

        repository.delete(found);

        return found;
    }

    @Transactional
    public void verify(String username) {
        User found = get(username);
        found.verify();
        repository.save(found);
    }

    @Transactional(readOnly = true)
    public List<User> find(String usernameQuery, String caller) {

        List<User> foundUsers = repository.findAllByUsernameContains(usernameQuery)
                .stream()
                .filter(user -> !user.isBanned())
                .collect(Collectors.toList());

        foundUsers = filterBlocked(caller, foundUsers);

        return foundUsers;

    }

    @Transactional(readOnly = true)
    public List<User> findBySpecification(UserSpecification specification) {
        List<User> found = repository.findAll(specification)
                .stream()
                .filter(user -> !user.isBanned())
                .collect(Collectors.toList());
        log.info("Found {} users by specification", found.size());
        return found;
    }

    public boolean isPrivate(String username) {
        log.info("Getting user profile visibility for an user: '{}'", username);
        User user = repository.findById(username).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("Profile for username '%s' doesn't exist!", username)
                )
        );
        bannedCheck(user);
        return user.isPrivate();
    }

    public boolean isBanned(String username) {
        log.info("Checking if user '{}' has been banned.", username);
        User user = repository.findById(username).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("Profile for username '%s' doesn't exist!", username)
                )
        );
        return user.isBanned();
    }

    @Transactional(readOnly = true)
    public List<User> findTaggable(String usernameQuery, String caller) {
        log.info("Finding taggable users by username search query: '{}'", usernameQuery);
        List<User> foundUsers = new ArrayList<>();

        for (User user : repository.findAllByUsernameContains(usernameQuery)) {
            if (user.isTaggable() && !user.isBanned()) {
                foundUsers.add(user);
            }
        }
        foundUsers = filterBlocked(caller, foundUsers);

        return foundUsers;
    }

    @Transactional
    public User update(String username, PersonalData personalData) {
        User found = get(username);
        bannedCheck(found);
        log.info("Updating personal data for user '{}'", username);

        found.setPersonalData(personalData);
        return repository.save(found);
    }

    @Transactional
    public User update(String username, PrivacyData privacyData) {
        User found = get(username);
        bannedCheck(found);
        log.info("Updating privacy data for user '{}'", username);

        found.setPrivacyData(privacyData);
        publishUserUpdated(found);
        return repository.save(found);
    }

    private void publishUserUpdated(User user) {
        UserUpdatedEvent event = new UserUpdatedEvent(UUID.randomUUID().toString(),
                UserEventPayloadMapper.toPayload(user));

        log.info("Publishing an user updated event {}", event);

        publisher.publishEvent(event);
    }

    @Transactional
    public User update(String username, NotificationPreferences preferences) {
        User found = get(username);
        bannedCheck(found);
        log.info("Updating privacy data for user '{}'", username);

        found.setNotificationPreferences(preferences);
        return repository.save(found);
    }

    @Transactional(readOnly = true)
    public UserStats getStats(String username) {
        if (!repository.existsById(username)) {
            throw new EntityNotFoundException(
                    String.format("User with username '%s' not found!", username));
        }

        bannedCheck(username);

        FollowerStats followerStats = userGraphClient.getFollowerStats(username);
        PostStats postStats = postClient.getPostStats(username);
        log.info("Fetched user stats for user '{}'", username);
        return statsMapper.map(followerStats, postStats);
    }

    private void bannedCheck(String username) {
        User found = get(username);
        bannedCheck(found);
    }

    private void bannedCheck(User foundUser) {
        if (foundUser.isBanned())
            throw new BannedException(String.format("User '%s' is banned!", foundUser.getUsername()));
    }

    private List<User> filterBlocked(String caller, List<User> foundUsers) {
        return foundUsers
                .stream()
                .filter(user -> !userGraphClient.hasBlocked(caller, user.getUsername()).isBlocked()
                        && !userGraphClient.isBlockedBy(caller, user.getUsername()).isBlocked())
                .collect(Collectors.toList());
    }

}
