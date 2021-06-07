package rs.ac.uns.ftn.nistagram.feed.services;


import com.sun.tools.jconsole.JConsolePlugin;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.PostFeedEntry;
import rs.ac.uns.ftn.nistagram.feed.domain.user.User;
import rs.ac.uns.ftn.nistagram.feed.exceptions.EntityNotFoundException;
import rs.ac.uns.ftn.nistagram.feed.http.UserGraphClient;
import rs.ac.uns.ftn.nistagram.feed.http.payload.UserPayload;
import rs.ac.uns.ftn.nistagram.feed.repositories.PostFeedRepository;
import rs.ac.uns.ftn.nistagram.feed.repositories.UserRepository;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class FeedService {

    private final UserRepository userRepository;
    private final UserGraphClient userGraphClient;
    private final PostFeedRepository postFeedRepository;

    @Transactional
    public void addToFeeds(PostFeedEntry postFeedEntry) {
        log.info("Posts by {} are being added to the feed of all his followers",
                postFeedEntry.getPublisher());

        var followers = userGraphClient
                .getFollowers(postFeedEntry.getPublisher());

        populateFeeds(followers, postFeedEntry);

        log.info("Post by {} is successfully added to the feed of all his followers",
                postFeedEntry.getPublisher());
    }

    private void populateFeeds( List<UserPayload> followers, PostFeedEntry postFeedEntry) {
        followers.forEach(follower -> {
            User foundFollower = userRepository.getOne(follower.getUsername());
            postFeedEntry.addUser(foundFollower);
            foundFollower.addToFeed(postFeedEntry);
        });
    }
    @Transactional
    public void removeFromFeeds(PostFeedEntry postFeedEntry) {
        log.info("Posts by {} are being removed from all of his followers feeds",
                postFeedEntry.getPublisher());

        var followers = userGraphClient.getFollowers(postFeedEntry.getPublisher());

        clearFeeds(followers, postFeedEntry);

        log.info("Post by {} is successfully removed from all of his followers feeds", postFeedEntry.getPublisher());
    }

    private void clearFeeds(List<UserPayload> followers, PostFeedEntry postFeedEntry) {
        PostFeedEntry foundEntry = findPostFeedEntry(postFeedEntry);
        followers.forEach(follower -> {
            User foundFollower = userRepository.getOne(follower.getUsername());
            foundEntry.removeUser(foundFollower);
            foundFollower.removeFromFeed(foundEntry);
            userRepository.save(foundFollower);
        });
        postFeedRepository.delete(foundEntry);

    }

    private PostFeedEntry findPostFeedEntry(PostFeedEntry postFeedEntry) {
        return  postFeedRepository
                .findAll()
                .stream()
                .filter(feedEntry -> feedEntry.getPostId().equals(postFeedEntry.getPostId()))
                .findFirst()
                .orElseThrow(()
                    -> new EntityNotFoundException(
                        String.format("Post feed entry with assigned with post with an id %s doesn't exist",
                            postFeedEntry.getPostId())));
    }
}
