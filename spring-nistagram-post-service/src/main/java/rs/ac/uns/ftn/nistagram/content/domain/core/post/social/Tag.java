package rs.ac.uns.ftn.nistagram.content.domain.core.post.social;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "tags")
@Getter
@Setter
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @NotEmpty
    private String tag;
    @ManyToOne
    private Post post;
}
