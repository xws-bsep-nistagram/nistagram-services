package rs.ac.uns.ftn.nistagram.feed.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.StoryFeedEntry;

import java.util.List;

public interface StoryFeedRepository extends JpaRepository<StoryFeedEntry, Long> {

    @Query("select sfe from StoryFeedEntry sfe join sfe.users user where user.username =:username")
    List<StoryFeedEntry> findAllByUsername(String username);

    @Query("select sfe from StoryFeedEntry sfe where sfe.publisher =:username")
    List<StoryFeedEntry> findAllByPublisher(String username);

    void deleteAllByStoryId(Long storyId);

}
