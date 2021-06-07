package rs.ac.uns.ftn.nistagram.content.domain.core.post.social;

import lombok.*;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
