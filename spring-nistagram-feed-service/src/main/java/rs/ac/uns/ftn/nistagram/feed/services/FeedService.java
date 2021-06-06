package rs.ac.uns.ftn.nistagram.feed.services;


import com.sun.tools.jconsole.JConsolePlugin;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.PostFeedEntry;
import rs.ac.uns.ftn.nistagram.feed.domain.user.User;
import rs.ac.uns.ftn.nistagram.feed.http.UserGraphClient;
import rs.ac.uns.ftn.nistagram.feed.http.payload.UserPayload;
import rs.ac.uns.ftn.nistagram.feed.repositories.UserRepository;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class FeedService {

    private final UserRepository userRepository;
    private final UserGraphClient userGraphClient;

    @Transactional
    public void addToFeeds(PostFeedEntry postFeedEntry) {
        log.info("Posts by {} are being added to the feed of all his followers",
                postFeedEntry.getPublisher());

        var followers = userGraphClient
                .getFollowers(postFeedEntry.getPublisher());

        populateFeeds(followers, postFeedEntry);

        log.info("Posts by {} are successfully added to the feed of all his followers",
                postFeedEntry.getPublisher());
    }

    //Using a copy constructor because otherwise, jpa would just update PostFeedEntry database
    //entry in each iteration
    private void populateFeeds( List<UserPayload> followers, PostFeedEntry postFeedEntry) {
        followers.forEach(follower -> {
            var feedEntry = new PostFeedEntry(postFeedEntry);
            User foundFollower = userRepository.getOne(follower.getUsername());
            feedEntry.setUser(foundFollower);
            foundFollower.addToFeed(feedEntry);
        });
    }

}
