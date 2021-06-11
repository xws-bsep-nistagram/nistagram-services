package rs.ac.uns.ftn.nistagram.content.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@AllArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;
    private final StoryHighlightsRepository highlightsRepository;
    private final External.GraphClientWrapper graphClient;
    private final ContentProducer contentProducer;


    public void create(Story story) {
        // TODO For a reshare story, check whether the author of the share, follows the person who created the post!
        log.info("[STORY][C][R][CALL={}]", story.getAuthor());

        story.setTime(LocalDateTime.now());
        storyRepository.save(story);
        log.info("[STORY][C][C][CALL={}]", story.getAuthor());

        contentProducer.publishStoryCreated(story);
        log.info("[STORY][C][P][CALL={}]", story.getAuthor());
    }

    @Transactional
    public void delete(long storyId, String caller) {
        log.info("[STORY][D][R][CALL={}][ID={}]", caller, storyId);

        Story story = storyRepository.findById(storyId).orElseThrow();
        if (!story.getAuthor().equals(caller))
            throw new OwnershipException();
        else {
            storyRepository.delete(story);
            log.info("[STORY][D][C][CALL={}][ID={}]", caller, storyId);

            contentProducer.publishStoryDeleted(story);
            log.info("[STORY][D][P][CALL={}][ID={}]", caller, storyId);
        }
    }

    public List<Story> getByUsername(String username, String caller) {
        log.info("[STORY][G][R][CALL={}][TGT={}]", caller, username);
        graphClient.assertFollow(caller, username);

        try {
            graphClient.assertCloseFriends(caller, username);
        }
        catch (RuntimeException ignore) {
            log.info("[STORY-PUBLIC][G][C][CALL={}][TGT={}]", caller, username);
            return storyRepository.getNonCloseFriendsByUsernameAfterDate(username, twentyFourHoursAgo());
        }

        log.info("[STORY-CF][G][C][CALL={}][TGT={}]", caller, username);
        return storyRepository.getAllByUsernameAfterDate(username, twentyFourHoursAgo());
    }

    public Story getById(Long storyId, String caller) {
        log.info("[STORY][G][R][CALL={}][ID={}]", caller, storyId);

        Story story = storyRepository.findById(storyId).orElseThrow();

        graphClient.assertFollow(caller, story.getAuthor());

        if(story.isCloseFriends())
            graphClient.assertCloseFriends(caller, story.getAuthor());

        log.info("[STORY][G][C][CALL={}][ID={}]", caller, storyId);
        return story;
    }


    public List<Story> getByUsername(String username){
        log.info("[STORY][G][R][TGT={}]", username);
        return storyRepository.getNonCloseFriendsByUsernameAfterDate(username, twentyFourHoursAgo());
    }

    public List<Story> getOwnActive(String caller) {
        log.info("[STORY][G][R][CALL={}][TGT={}]", caller, caller);
        return storyRepository.getAllByUsernameAfterDate(caller, twentyFourHoursAgo());
    }

    public List<Story> getOwnAll(String caller) {
        log.info("[STORY][G][R][CALL={}][TGT={}]", caller, caller);
        return storyRepository.getAllByUsername(caller);
    }

    private LocalDateTime twentyFourHoursAgo() { return LocalDateTime.now().minus(Duration.ofDays(1));}

    public void createStoryHighlights(String name, String caller) {
        log.info("[HIGH][C][R][CALL={}][ID={}]", caller, name);
        highlightsRepository.save(  // TODO This allows non-unique highlight sections (with the same name)
                StoryHighlight.builder().owner(caller).name(name).build()
        );
        log.info("[HIGH][C][C][CALL={}][ID={}]", caller, name);
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
        log.info("[HIGH-STORY][C][C][IDS={}][IDH={}]", storyId, highlightsId);
    }

    public List<StoryHighlight> getHighlightsByUsername(String username, String caller) {
        log.info("[HIGH][G][R][CALL={}][TGT={}]", caller, username);

        graphClient.assertFollow(caller, username);
        List<StoryHighlight> highlights = highlightsRepository.getByUsername(username);
        log.info("[HIGH][G][C][CALL={}][TGT={}]", caller, username);

        return highlights;
    }

    public List<Story> getStoriesFromHighlight(long highlightId, String caller) {
        log.info("[HIGH-STORY][G][R][CALL={}][ID={}]", caller, highlightId);

        StoryHighlight highlight = highlightsRepository.findById(highlightId).orElseThrow();
        List<Story> highlightStories = highlight.getStories().stream().map(HighlightedStory::getStory).collect(Collectors.toList());

        // TODO Maybe modify graph client to respond with a DTO which provides both of these info?
        graphClient.assertFollow(caller, highlight.getOwner());

        try {
            graphClient.assertCloseFriends(caller, highlight.getOwner());
        }
        catch (RuntimeException ignore) {
            log.info("[HIGH-STORY][G][C][CALL={}][ID={}]", caller, highlightId);
            return highlightStories.stream().filter(story -> !story.isCloseFriends()).collect(Collectors.toList());
        }

        log.info("[HIGH-STORY][G][C][CALL={}][ID={}]", caller, highlightId);
        return highlightStories;
    }

    public void deleteHighlight(long highlightId, String caller) {
        log.info("[HIGH][D][R][CALL={}][ID={}]", caller, highlightId);

        StoryHighlight highlight = highlightsRepository.findById(highlightId).orElseThrow();
        if (!highlight.getOwner().equals(caller))
            throw new OwnershipException();
        else highlightsRepository.delete(highlight);
        log.info("[HIGH][D][C][CALL={}][ID={}]", caller, highlightId);
    }
}
