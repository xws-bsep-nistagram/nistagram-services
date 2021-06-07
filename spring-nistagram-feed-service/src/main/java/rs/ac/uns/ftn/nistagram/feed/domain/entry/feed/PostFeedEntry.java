package rs.ac.uns.ftn.nistagram.feed.domain.entry.feed;

import lombok.*;
import rs.ac.uns.ftn.nistagram.feed.domain.user.User;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostFeedEntry extends FeedEntry {

    private Long postId;

    public PostFeedEntry(PostFeedEntry feedEntry){
        super(feedEntry.getPublisher(), feedEntry.getCreatedAt());
        this.postId = feedEntry.postId;
    }

    public PostFeedEntry(String publisher, LocalDateTime createdAt, Long postId){
        super(publisher, createdAt);
        this.postId = postId;
    }

    public void addUser(User user) {
        super.addUser(user);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    public void removeUser(User user) {
        super.removeUser(user);
    }
}
