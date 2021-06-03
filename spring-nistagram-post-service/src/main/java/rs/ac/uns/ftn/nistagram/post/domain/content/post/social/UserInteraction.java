package rs.ac.uns.ftn.nistagram.post.domain.content.post.social;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.post.domain.content.post.Post;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_interactions")
@Getter
@Setter
public class UserInteraction {
    @Id
    private long id;
    private String username;
    @ManyToOne
    private Post post;
    private Sentiment sentiment;

    public enum Sentiment { LIKE, DISLIKE }
}
