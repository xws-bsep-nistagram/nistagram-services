package rs.ac.uns.ftn.nistagram.post.domain.content.post.social;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.post.domain.content.post.Post;

import javax.persistence.*;

@Entity
@Table(name = "hashtags")
@Getter
@Setter
public class HashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String tag;
    @ManyToOne
    private Post post;
}
