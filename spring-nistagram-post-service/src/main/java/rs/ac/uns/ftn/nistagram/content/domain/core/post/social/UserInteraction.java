package rs.ac.uns.ftn.nistagram.content.domain.core.post.social;

import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;

import javax.persistence.*;

@Entity
@Table(name = "user_interactions")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserInteraction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String username;
    @ManyToOne
    private Post post;
    private Sentiment sentiment;

    public UserInteraction() {}

    public enum Sentiment { LIKE, DISLIKE }
}
