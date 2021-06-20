package rs.ac.uns.ftn.nistagram.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import rs.ac.uns.ftn.nistagram.user.infrastructure.exceptions.EntityNotFoundException;
import rs.ac.uns.ftn.nistagram.user.messaging.producers.UserProducer;
import rs.ac.uns.ftn.nistagram.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ProfileService {

    private final UserRepository repository;
    private final UserProducer userProducer;
    private final UserGraphClient userGraphClient;
    private final PostClient postClient;
    private final UserStatsMapper statsMapper;


    @Transactional(readOnly = true)
    public User get(String username) {
        log.info("Getting user by username: '{}'", username);
        return repository.findById(username).orElseThrow(() ->
            new EntityNotFoundException(
                    String.format("Profile for username '%s' doesn't exist!", username)
            )
        );
    }
    @Transactional(readOnly = true)
    public List<User> find(String usernameQuery) {
        return repository.findAllByUsernameContains(usernameQuery);
    }

    public boolean isPrivate(String username){
        log.info("Getting user profile visibility for an user: '{}'",username);
        User user = repository.findById(username).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("Profile for username '%s' doesn't exist!", username)
                )
        );
        return user.isPrivate();
    }

    @Transactional(readOnly = true)
    public List<User> findTaggable(String usernameQuery) {
        log.info("Finding taggable users by username search query: '{}'", usernameQuery);
        return repository
                .findAllByUsernameContains(usernameQuery)
                .stream()
                .filter(User::isTaggable)
                .collect(Collectors.toList());
    }

    @Transactional
    public User update(String username, PersonalData personalData) {
        User found = get(username);

        log.info("Updating personal data for user '{}'", username);

        found.setPersonalData(personalData);
        return repository.save(found);
    }

    @Transactional
    public User update(String username, PrivacyData privacyData) {
        User found = get(username);

        log.info("Updating privacy data for user '{}'", username);

        found.setPrivacyData(privacyData);
        userProducer.publishUserUpdated(found);
        return repository.save(found);
    }

    @Transactional
    public User update(String username, NotificationPreferences preferences) {
        User found = get(username);

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
        FollowerStats followerStats = userGraphClient.getFollowerStats(username);
        PostStats postStats = postClient.getPostStats(username);
        log.info("Fetched user stats for user '{}'", username);
        return statsMapper.map(followerStats, postStats);
    }

    @Transactional
    public void verify(String username) {
        User found = get(username);
        found.verify();
        repository.save(found);
    }

}
