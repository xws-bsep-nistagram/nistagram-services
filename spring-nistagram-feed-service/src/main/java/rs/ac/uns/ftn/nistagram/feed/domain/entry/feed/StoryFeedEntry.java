package rs.ac.uns.ftn.nistagram.feed.domain.entry.feed;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class StoryFeedEntry extends FeedEntry{

    private Long storyId;

    public StoryFeedEntry(String publisher, LocalDateTime createdAt, Long storyId){
        super(publisher, createdAt);
        this.storyId = storyId;
    }
}
