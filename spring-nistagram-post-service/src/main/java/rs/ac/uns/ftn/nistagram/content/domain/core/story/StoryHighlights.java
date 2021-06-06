package rs.ac.uns.ftn.nistagram.content.domain.core.story;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "story_highlights")
@Getter
@Setter
public class StoryHighlights {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String author;
    @ManyToMany
    private List<Story> stories;
}
