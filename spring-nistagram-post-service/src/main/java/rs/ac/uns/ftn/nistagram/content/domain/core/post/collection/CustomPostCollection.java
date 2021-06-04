package rs.ac.uns.ftn.nistagram.content.domain.core.post.collection;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "collections")
@Getter
@Setter
public class CustomPostCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String owner;
    private String name;
    @ManyToMany // A single post can be within multiple collections + collection has multiple posts
    private List<Post> posts;
}
