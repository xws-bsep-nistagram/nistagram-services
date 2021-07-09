package rs.ac.uns.ftn.nistagram.feed.services;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.FeedEntry;
import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.PostFeedEntry;
import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.StoryFeedEntry;
import rs.ac.uns.ftn.nistagram.feed.domain.user.User;
import rs.ac.uns.ftn.nistagram.feed.exceptions.EntityNotFoundException;
import rs.ac.uns.ftn.nistagram.feed.http.ContentClient;
import rs.ac.uns.ftn.nistagram.feed.http.UserGraphClient;
import rs.ac.uns.ftn.nistagram.feed.http.mappers.UserContentMapper;
import rs.ac.uns.ftn.nistagram.feed.http.payload.user.UserPayload;
import rs.ac.uns.ftn.nistagram.feed.repositories.PostFeedRepository;
import rs.ac.uns.ftn.nistagram.feed.repositories.StoryFeedRepository;
import rs.ac.uns.ftn.nistagram.feed.repositories.UserRepository;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class FeedService {

    private final UserRepository userRepository;
    private final UserGraphClient userGraphClient;
    private final ContentClient contentClient;
    private final PostFeedRepository postFeedRepository;
    private final StoryFeedRepository storyFeedRepository;

    @Transactional(readOnly = true)
    public List<PostFeedEntry> getPostFeedByUsername(String username) {
        log.info("Request for getting all the post feed entries for {} received", username);

        var postFeedEntries = postFeedRepository.findAllByUsername(username);

        log.info("Found '{}' post entries for an user '{}'", postFeedEntries.size(), username);

        if (postFeedEntries.size() != 0)
            postFeedEntries.sort(Comparator.comparing(FeedEntry::getCreatedAt).reversed());


        return postFeedEntries;
    }

    @Transactional(readOnly = true)
    public List<StoryFeedEntry> getStoryFeedByUsername(String username) {
        log.info("Request for getting all the story feed entries for an user '{}' received", username);

        var storyFeedEntries = storyFeedRepository
                .findAllByUsername(username)
                .stream()
                .filter(e -> !e.getCloseFriends() && !e.isAd())
                .collect(Collectors.toList());

        log.info("Found '{}' story feed entries for an user '{}'", storyFeedEntries.size(), username);

        if (storyFeedEntries.size() != 0)
            storyFeedEntries.sort(Comparator.comparing(FeedEntry::getCreatedAt).reversed());

        return storyFeedEntries;
    }

    @Transactional(readOnly = true)
    public List<StoryFeedEntry> getCloseFriendStoryFeedByUsername(String username) {

        log.info("Request for getting all the close friend story feed entries for an user '{}' received",
                username);

        var storyFeedEntries = storyFeedRepository
                .findAllByUsername(username)
                .stream().filter(entry -> entry.getCloseFriends() && !entry.isAd())
                .collect(Collectors.toList());

        log.info("Found '{}' story feed entries for an user '{}'", storyFeedEntries.size(), username);

        if (storyFeedEntries.size() != 0)
            storyFeedEntries.sort(Comparator.comparing(FeedEntry::getCreatedAt).reversed());

        return storyFeedEntries;
    }

    @Transactional(readOnly = true)
    public List<StoryFeedEntry> getStoryCampaignsByUsername(String username) {
        log.info("Request for getting all the story ad campaigns for user '{}' received",
                username);
        List<StoryFeedEntry> all = storyFeedRepository.findAllByUsername(username)
                .stream().filter(StoryFeedEntry::isAd)
                .collect(Collectors.toList());

        log.info("Found '{}' story feed entries for an user '{}'", all.size(), username);
        if (all.size() != 0)
            all.sort(Comparator.comparing(FeedEntry::getCreatedAt).reversed());

        return all;
    }

    @Transactional
    public void addToPostFeeds(PostFeedEntry postFeedEntry) {
        log.info("Post by '{}' are being added to the all of his follower feeds",
                postFeedEntry.getPublisher());

        var followers = userGraphClient
                .getFollowers(postFeedEntry.getPublisher());

        populatePostFeeds(followers, postFeedEntry);
    }

    @Transactional
    public void addCampaignToPostFeeds(List<UserPayload> users, PostFeedEntry postFeedEntry) {
        log.info("Campaign being added to targeted user groups");
        populatePostFeeds(users, postFeedEntry);
    }

    @Transactional
    public void addToStoryFeeds(StoryFeedEntry storyFeedEntry) {
        log.info("Story by '{}' are being added to the all of his follower feeds",
                storyFeedEntry.getPublisher());

        List<UserPayload> followers;
        if (storyFeedEntry.getCloseFriends())
            followers = userGraphClient.getCloseFriends(storyFeedEntry.getPublisher());
        else
            followers = userGraphClient.getFollowers(storyFeedEntry.getPublisher());

        populateStoryFeeds(followers, storyFeedEntry);
    }

    @Transactional
    public void addCampaignToStoryFeeds(List<UserPayload> users, StoryFeedEntry storyFeedEntry) {
        log.info("Campaign being added to targeted user group");
        populateStoryFeeds(users, storyFeedEntry);
    }

    @Transactional
    public void removeFromPostFeeds(PostFeedEntry postFeedEntry) {
        log.info("Post by '{}' is being removed from all of his follower feeds",
                postFeedEntry.getPublisher());

        var followers = userGraphClient.getFollowers(postFeedEntry.getPublisher());

        clearPostFeeds(followers, postFeedEntry);
    }

    @Transactional
    public void removeFromStoryFeeds(StoryFeedEntry storyFeedEntry) {
        log.info("Story by '{}' is being removed from all of his follower feeds",
                storyFeedEntry.getPublisher());

        List<UserPayload> followers;
        if (storyFeedEntry.getCloseFriends())
            followers = userGraphClient.getCloseFriends(storyFeedEntry.getPublisher());
        else
            followers = userGraphClient.getFollowers(storyFeedEntry.getPublisher());

        clearStoryFeeds(followers, storyFeedEntry);
    }

    @Transactional
    public void addTargetsContent(String subject, String target) {
        log.info("All the '{}' available post and stories are being added to a '{}' feed", target, subject);

        var foundSubject = userRepository.getById(subject);

        appendTargetPostCollection(foundSubject, getUsersPosts(target));
        appendTargetStoryCollection(foundSubject, getUsersStories(target));

        log.info("All the '{}' available post and stories successfully added to a '{}' feed", target, subject);

    }

    private void appendTargetStoryCollection(User foundSubject, List<StoryFeedEntry> targetStoryCollection) {
        targetStoryCollection.forEach(storyFeedEntry -> {
            StoryFeedEntry entry;
            try {
                entry = findStoryFeedEntry(storyFeedEntry);
            } catch (EntityNotFoundException e) {
                entry = storyFeedEntry;
            }
            entry.addUser(foundSubject);
            foundSubject.addToStoryFeed(entry);
            userRepository.save(foundSubject);

        });
    }

    private void appendTargetPostCollection(User foundSubject, List<PostFeedEntry> targetPostCollection) {
        targetPostCollection.forEach(postFeedEntry -> {
            PostFeedEntry entry;
            try {
                entry = findPostFeedEntry(postFeedEntry);
            } catch (EntityNotFoundException e) {
                entry = postFeedEntry;
            }
            entry.addUser(foundSubject);
            foundSubject.addToPostFeed(entry);
            userRepository.save(foundSubject);
        });
    }

    private List<StoryFeedEntry> getUsersStories(String target) {
        return contentClient
                .getStoriesByUsername(target)
                .stream()
                .map(UserContentMapper::toDomain)
                .collect(Collectors.toList());
    }

    private List<PostFeedEntry> getUsersPosts(String target) {
        return contentClient
                .getPostsByUsername(target)
                .stream()
                .map(UserContentMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional
    public void removeTargetsContent(String subject, String target) {
        log.info("All the '{}' post and stories are being removed from a '{}' feed", target, subject);

        User foundSubject = userRepository.getById(subject);
        removeTargetsPostFromSubjectsFeed(target, foundSubject);
        removeTargetsStoriesFromSubjectsFeed(target, foundSubject);

        log.info("All the '{}' post and stories successfully removed from a '{}' feed", target, subject);
    }

    private void removeTargetsStoriesFromSubjectsFeed(String target, User foundSubject) {
        List<StoryFeedEntry> subjectsStoryFeedEntries = storyFeedRepository
                .findAllByUsername(foundSubject.getUsername());
        subjectsStoryFeedEntries.forEach(storyFeedEntry -> {
            if (storyFeedEntry.getPublisher().equals(target)) {
                storyFeedEntry.removeUser(foundSubject);
                foundSubject.removeFromStoryFeed(storyFeedEntry);
            }
        });
    }

    private void removeTargetsPostFromSubjectsFeed(String target, User foundSubject) {
        List<PostFeedEntry> subjectsPostFeedEntries = postFeedRepository
                .findAllByUsername(foundSubject.getUsername());
        subjectsPostFeedEntries.forEach(postFeedEntry -> {
            if (postFeedEntry.getPublisher().equals(target)) {
                postFeedEntry.removeUser(foundSubject);
                foundSubject.removeFromPostFeed(postFeedEntry);
            }
        });
    }

    private void clearPostFeeds(List<UserPayload> followers, PostFeedEntry postFeedEntry) {
        if (followers.isEmpty()) {
            log.warn("User '{}' has no followers", postFeedEntry.getPublisher());
            return;
        }

        PostFeedEntry foundEntry = findPostFeedEntry(postFeedEntry);
        followers.forEach(follower -> {
            User foundFollower = userRepository.getById(follower.getUsername());
            foundEntry.removeUser(foundFollower);
            foundFollower.removeFromPostFeed(foundEntry);
            userRepository.save(foundFollower);
        });
        postFeedRepository.delete(foundEntry);

        log.info("Post by '{}' is successfully removed from all of his follower feeds",
                postFeedEntry.getPublisher());

    }

    private void clearStoryFeeds(List<UserPayload> followers, StoryFeedEntry storyFeedEntry) {
        if (followers.isEmpty()) {
            log.warn("User '{}' has no followers", storyFeedEntry.getPublisher());
            return;
        }

        StoryFeedEntry foundEntry = findStoryFeedEntry(storyFeedEntry);
        followers.forEach(follower -> {
            User foundFollower = userRepository.getById(follower.getUsername());
            foundEntry.removeUser(foundFollower);
            foundFollower.removeFromStoryFeed(foundEntry);
            userRepository.save(foundFollower);
        });

        storyFeedRepository.delete(foundEntry);
        log.info("Story by '{}' is successfully removed from all of his follower feeds",
                storyFeedEntry.getPublisher());
    }

    private PostFeedEntry findPostFeedEntry(PostFeedEntry postFeedEntry) {
        return postFeedRepository
                .findAll()
                .stream()
                .filter(feedEntry -> feedEntry.getPostId().equals(postFeedEntry.getPostId()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Post feed entry assigned with a post with an id %s doesn't exist",
                                postFeedEntry.getPostId())));
    }

    private StoryFeedEntry findStoryFeedEntry(StoryFeedEntry storyFeedEntry) {
        return storyFeedRepository
                .findAll()
                .stream()
                .filter(feedEntry -> feedEntry.getStoryId().equals(storyFeedEntry.getStoryId()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Story feed entry assigned with a story with an id %s doesn't exist",
                                storyFeedEntry.getStoryId())
                ));
    }

    private void populatePostFeeds(List<UserPayload> followers, PostFeedEntry postFeedEntry) {
        if (followers.isEmpty()) {
            log.warn("User '{}' has no followers", postFeedEntry.getPublisher());
            return;
        }

        followers.forEach(follower -> {
            User foundFollower = userRepository.getById(follower.getUsername());
            postFeedEntry.addUser(foundFollower);
            foundFollower.addToPostFeed(postFeedEntry);
        });

        log.info("Post by {} is successfully added to all of his follower feeds",
                postFeedEntry.getPublisher());
    }

    private void populateStoryFeeds(List<UserPayload> followers, StoryFeedEntry storyFeedEntry) {
        if (followers.isEmpty()) {
            log.warn("User '{}' has no followers", storyFeedEntry.getPublisher());
            return;
        }

        followers.forEach(follower -> {
            User foundFollower = userRepository.getById(follower.getUsername());
            storyFeedEntry.addUser(foundFollower);
            foundFollower.addToStoryFeed(storyFeedEntry);
        });

        log.info("'{}' by '{}' are successfully added to all of his follower feeds",
                storyFeedEntry.getCloseFriends() ? "Close friend stories" : "Stories",
                storyFeedEntry.getPublisher());

    }

    @Transactional
    public void deleteAds(Long campaignId) {
        postFeedRepository.deleteAllByPostId(campaignId);
        storyFeedRepository.deleteAllByStoryId(campaignId);
    }
}
