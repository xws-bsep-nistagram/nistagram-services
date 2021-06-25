package rs.ac.uns.ftn.nistagram.feed.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.PostFeedEntry;
import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.StoryFeedEntry;
import rs.ac.uns.ftn.nistagram.feed.domain.user.User;
import rs.ac.uns.ftn.nistagram.feed.exceptions.EntityAlreadyExistsException;
import rs.ac.uns.ftn.nistagram.feed.exceptions.EntityNotFoundException;
import rs.ac.uns.ftn.nistagram.feed.repositories.PostFeedRepository;
import rs.ac.uns.ftn.nistagram.feed.repositories.StoryFeedRepository;
import rs.ac.uns.ftn.nistagram.feed.repositories.UserRepository;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PostFeedRepository postFeedRepository;
    private final StoryFeedRepository storyFeedRepository;

    public void create(User user) {
        userPresenceCheck(user);
        log.info("User creation request for an user '{}' received", user.getUsername());
        userRepository.save(user);
        log.info("User '{}' has been successfully created", user.getUsername());
    }

    public void delete(User user) {
        userPresenceCheck(user);

        log.info("User deletion request for an user '{}' received", user.getUsername());

        clearUserFeed(user);
        clearFollowersFeed(user);
        userRepository.deleteById(user.getUsername());

        log.info("User '{}' has been successfully deleted", user.getUsername());
    }

    private void clearUserFeed(User user) {
        List<PostFeedEntry> postFeedEntries = postFeedRepository.findAllByUsername(user.getUsername());
        postFeedEntries.forEach(postFeedRepository::delete);

        List<StoryFeedEntry> storyFeedEntries = storyFeedRepository.findAllByUsername(user.getUsername());
        storyFeedEntries.forEach(storyFeedRepository::delete);
    }

    private void clearFollowersFeed(User user) {
        List<PostFeedEntry> postFeedEntries = postFeedRepository.findAllByPublisher(user.getUsername());
        postFeedEntries.forEach(postFeedRepository::delete);

        List<StoryFeedEntry> storyFeedEntries = storyFeedRepository.findAllByPublisher(user.getUsername());
        storyFeedEntries.forEach(storyFeedRepository::delete);


    }

    private void userPresenceCheck(User user) {
        if (userRepository.existsById(user.getUsername())) {
            var message = String.format("User '%s' already exist", user.getUsername());
            log.warn(message);
            throw new EntityAlreadyExistsException(message);
        }
    }

    private void userAbscenceCheck(User user) {
        if (!userRepository.existsById(user.getUsername())) {
            var message = String.format("User '%s' doesn't exist", user.getUsername());
            log.warn(message);
            throw new EntityNotFoundException(message);
        }
    }


}
