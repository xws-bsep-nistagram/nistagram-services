package rs.ac.uns.ftn.nistagram.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.user.domain.user.PersonalData;
import rs.ac.uns.ftn.nistagram.user.domain.user.PrivacyData;
import rs.ac.uns.ftn.nistagram.user.domain.user.User;
import rs.ac.uns.ftn.nistagram.user.domain.user.preferences.NotificationPreferences;
import rs.ac.uns.ftn.nistagram.user.infrastructure.exceptions.EntityNotFoundException;
import rs.ac.uns.ftn.nistagram.user.repository.UserRepository;

@Service
public class ProfileService {

    private final UserRepository repository;

    public ProfileService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public User get(String username) {
        return repository.findById(username).orElseThrow(() ->
            new EntityNotFoundException(
                    String.format("Profile for username '%s' doesn't exist!", username)
            )
        );
    }

    @Transactional
    public User update(String username, PersonalData personalData) {
        User found = get(username);
        found.setPersonalData(personalData);
        return repository.save(found);
    }

    @Transactional
    public User update(String username, PrivacyData privacyData) {
        User found = get(username);
        found.setPrivacyData(privacyData);
        return repository.save(found);
    }

    @Transactional
    public User update(String username, NotificationPreferences preferences) {
        User found = get(username);
        found.setNotificationPreferences(preferences);
        return repository.save(found);
    }

}
