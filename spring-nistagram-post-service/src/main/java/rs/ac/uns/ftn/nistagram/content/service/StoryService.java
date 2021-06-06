package rs.ac.uns.ftn.nistagram.content.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.content.domain.core.story.Story;
import rs.ac.uns.ftn.nistagram.content.repository.story.StoryRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StoryService {

    private final StoryRepository storyRepository;

    public StoryService(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
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

}
