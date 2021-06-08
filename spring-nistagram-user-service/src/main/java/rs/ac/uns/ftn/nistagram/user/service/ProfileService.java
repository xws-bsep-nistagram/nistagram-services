package rs.ac.uns.ftn.nistagram.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.user.domain.user.PersonalData;
import rs.ac.uns.ftn.nistagram.user.domain.user.PrivacyData;
import rs.ac.uns.ftn.nistagram.user.domain.user.User;
import rs.ac.uns.ftn.nistagram.user.domain.user.preferences.NotificationPreferences;
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



}
