package rs.ac.uns.ftn.nistagram.content.domain.core.post.collection;

import lombok.*;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;

import javax.persistence.*;

@Entity
@Table(name = "posts_in_collections")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostInCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @OneToOne
    private Post post;
    @OneToOne
    private CustomPostCollection collection;
}
