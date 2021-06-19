package rs.ac.uns.ftn.nistagram.content.domain.core.story;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class ShareStory extends Story {
    @ManyToOne
    private Post sharedPost;

    public Long getSharedPostId() {
        return sharedPost.getId();
    }
}
