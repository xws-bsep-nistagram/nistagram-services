package rs.ac.uns.ftn.nistagram.content.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.content.domain.core.story.Story;
import rs.ac.uns.ftn.nistagram.content.domain.core.story.StoryHighlights;
import rs.ac.uns.ftn.nistagram.content.repository.story.StoryHighlightsRepository;
import rs.ac.uns.ftn.nistagram.content.repository.story.StoryRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoryService {

    private final StoryRepository storyRepository;
    private final StoryHighlightsRepository highlightsRepository;

    public StoryService(StoryRepository storyRepository, StoryHighlightsRepository highlightsRepository) {
        this.storyRepository = storyRepository;
        this.highlightsRepository = highlightsRepository;
    }

    public void create(Story story) {
        story.setTime(LocalDateTime.now());
        System.out.println("LOKACIJA: [" + story.getLocation() + "]");
        storyRepository.save(story);
    }

    public List<Story> getByUsername(String username, String caller) {
        // TODO Check whether following or not, if not, reject!
        boolean closeFriends = true;

        if (closeFriends) return storyRepository.getAllByUsernameAfterDate(username, twentyFourHoursAgo());
        else return storyRepository.getNonCloseFriendsByUsernameAfterDate(username, twentyFourHoursAgo());
    }

    public List<Story> getOwnActive(String username) {
        return storyRepository.getAllByUsernameAfterDate(username, twentyFourHoursAgo());
    }

    public List<Story> getOwnAll(String username) {
        return storyRepository.getAllByUsername(username);
    }

    private LocalDateTime twentyFourHoursAgo() { return LocalDateTime.now().minus(Duration.ofDays(1));}

    public void createStoryHighlights(String name, String username) {
        highlightsRepository.save(  // TODO This allows non-unique highlight sections (with the same name)
                StoryHighlights.builder().author(username).name(name).build()
        );
    }

    public void addStoryToHighlights(long highlightsId, long storyId, String username) {
        Story story = storyRepository.findById(storyId).orElseThrow(RuntimeException::new);
        if (!story.getAuthor().equals(username))
            throw new RuntimeException("You are not the author of this story!");

        StoryHighlights highlights = highlightsRepository.findById(highlightsId).orElseThrow(RuntimeException::new);
        if (!highlights.getAuthor().equals(username))
            throw new RuntimeException("You are not the owner of this highlights section!");

        if (highlights.getStories().contains(story))
            throw new RuntimeException("This story is already in this highlights section!");

        highlights.getStories().add(story);
        highlightsRepository.save(highlights);
    }

    public List<StoryHighlights> getHighlightsByUsername(String username, String caller) {
        // TODO Check whether following!
        return highlightsRepository.getByUsername(username);
    }

    public List<Story> getStoriesFromHighlight(long highlightId, String caller) {
        StoryHighlights highlights = highlightsRepository.findById(highlightId).orElseThrow(RuntimeException::new);

        // TODO Check whether following!
        boolean closeFriends = true;

        List<Story> highlightStories = highlights.getStories();
        if (closeFriends)
            return highlightStories;
        else
            return highlights.getStories().stream().filter(story -> !story.isCloseFriends()).collect(Collectors.toList());
    }
}
