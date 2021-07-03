package rs.ac.uns.ftn.nistagram.feed.domain.entry.feed;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.feed.domain.user.User;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class StoryFeedEntry extends FeedEntry {

    private Long storyId;
    private Boolean closeFriends;

    public StoryFeedEntry(String publisher, LocalDateTime createdAt, Long storyId, Boolean closeFriends){
        super(publisher, createdAt);
        this.storyId = storyId;
        this.closeFriends = closeFriends;
    }

    public StoryFeedEntry(Long storyId) {
        super(null, LocalDateTime.now());
        this.storyId = storyId;
        super.setAd(true);
        this.closeFriends = false;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    public void addUser(User user){
        super.addUser(user);
    }
    public void removeUser(User user){
        super.removeUser(user);
    }
}
