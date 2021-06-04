package rs.ac.uns.ftn.nistagram.content.domain.core.post.collection;

import lombok.*;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;

import javax.persistence.*;

@Entity
@Table(name = "saved_posts")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SavedPost {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String username;
    @OneToOne
    private Post post;
}
