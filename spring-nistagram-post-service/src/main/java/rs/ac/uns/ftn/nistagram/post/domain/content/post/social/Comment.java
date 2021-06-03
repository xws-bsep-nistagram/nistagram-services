package rs.ac.uns.ftn.nistagram.post.domain.content.post.social;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.post.domain.content.post.Post;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @ManyToOne
    private Post post;
    private String author;
    private LocalDateTime time;
    private String text;
}
