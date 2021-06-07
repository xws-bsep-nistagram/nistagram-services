package rs.ac.uns.ftn.nistagram.content.domain.core.story;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "story_highlights")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoryHighlight {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String owner;
    private String name;
    @OneToMany(mappedBy = "highlight", cascade = CascadeType.ALL)
    private List<HighlightedStory> stories;
}
