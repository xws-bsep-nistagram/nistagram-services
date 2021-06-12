package rs.ac.uns.ftn.nistagram.content.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.content.communication.External;
import rs.ac.uns.ftn.nistagram.content.domain.core.story.HighlightedStory;
import rs.ac.uns.ftn.nistagram.content.domain.core.story.Story;
import rs.ac.uns.ftn.nistagram.content.domain.core.story.StoryHighlight;
import rs.ac.uns.ftn.nistagram.content.exception.NistagramException;
import rs.ac.uns.ftn.nistagram.content.exception.OwnershipException;
import rs.ac.uns.ftn.nistagram.content.messaging.producers.ContentProducer;
import rs.ac.uns.ftn.nistagram.content.repository.story.StoryHighlightsRepository;
import rs.ac.uns.ftn.nistagram.content.repository.story.StoryRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;
    private final StoryHighlightsRepository highlightsRepository;
    private final External.GraphClientWrapper graphClient;
    private final ContentProducer contentProducer;


    public void create(Story story) {
        // TODO For a reshare story, check whether the author of the share, follows the person who created the post!
        story.setTime(LocalDateTime.now());
        storyRepository.save(story);
        contentProducer.publishStoryCreated(story);
    }

    @Transactional
    public void delete(long storyId, String username) {
        Story story = storyRepository.findById(storyId).orElseThrow();
        if (!story.getAuthor().equals(username))
            throw new OwnershipException();
        else {
            storyRepository.delete(story);
            contentProducer.publishStoryDeleted(story);
        }
    }

    public List<Story> getByUsername(String username, String caller) {
        graphClient.assertFollow(caller, username);

        try {
            graphClient.assertCloseFriends(caller, username);
        }
        catch (RuntimeException ignore) {
            return storyRepository.getNonCloseFriendsByUsernameAfterDate(username, twentyFourHoursAgo());
        }

        return storyRepository.getAllByUsernameAfterDate(username, twentyFourHoursAgo());
    }
    public Story getById(Long storyId, String caller) {
        Story story = storyRepository.findById(storyId).orElseThrow();

        graphClient.assertFollow(caller, story.getAuthor());

        if(story.isCloseFriends())
            graphClient.assertCloseFriends(caller, story.getAuthor());

        return story;
    }


    public List<Story> getByUsername(String username){
        return storyRepository.getNonCloseFriendsByUsernameAfterDate(username, twentyFourHoursAgo());
    }

    public List<Story> getOwnActive(String caller) {
        return storyRepository.getAllByUsernameAfterDate(caller, twentyFourHoursAgo());
    }

    public List<Story> getOwnAll(String caller) {
        return storyRepository.getAllByUsername(caller);
    }

    private LocalDateTime twentyFourHoursAgo() { return LocalDateTime.now().minus(Duration.ofDays(1));}

    public void createStoryHighlights(String name, String caller) {
        highlightsRepository.save(  // TODO This allows non-unique highlight sections (with the same name)
                StoryHighlight.builder().owner(caller).name(name).build()
        );
    }

    public void addStoryToHighlights(long highlightsId, long storyId, String username) {
        Story story = storyRepository.findById(storyId).orElseThrow();
        if (!story.getAuthor().equals(username))
            throw new OwnershipException();

        StoryHighlight highlights = highlightsRepository.findById(highlightsId).orElseThrow();
        if (!highlights.getOwner().equals(username))
            throw new OwnershipException();

        if (highlights.getStories().stream().map(HighlightedStory::getStory).collect(Collectors.toList()).contains(story))
            throw new NistagramException("This story is already in this highlights section!");

        highlights.getStories().add(HighlightedStory.builder().highlight(highlights).story(story).build());
        highlightsRepository.save(highlights);
    }

    public List<StoryHighlight> getHighlightsByUsername(String username, String caller) {
        graphClient.assertFollow(caller, username);
        return highlightsRepository.getByUsername(username);
    }

    public List<Story> getStoriesFromHighlight(long highlightId, String caller) {
        StoryHighlight highlight = highlightsRepository.findById(highlightId).orElseThrow();
        List<Story> highlightStories = highlight.getStories().stream().map(HighlightedStory::getStory).collect(Collectors.toList());

        // TODO Maybe modify graph client to respond with a DTO which provides both of these info?
        graphClient.assertFollow(caller, highlight.getOwner());

        try {
            graphClient.assertCloseFriends(caller, highlight.getOwner());
        }
        catch (RuntimeException ignore) {
            return highlightStories.stream().filter(story -> !story.isCloseFriends()).collect(Collectors.toList());
        }

        return highlightStories;
    }

    public void deleteHighlight(long highlightId, String username) {
        StoryHighlight highlight = highlightsRepository.findById(highlightId).orElseThrow();
        if (!highlight.getOwner().equals(username))
            throw new OwnershipException();
        else highlightsRepository.delete(highlight);
    }


}
