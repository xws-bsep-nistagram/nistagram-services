package rs.ac.uns.ftn.nistagram.content.domain.core.story;

import lombok.*;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
public class ShareStory extends Story {
    @OneToOne
    private Post sharedPost;
}
