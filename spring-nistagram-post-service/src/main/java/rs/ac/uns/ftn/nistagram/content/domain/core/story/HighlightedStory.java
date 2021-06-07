package rs.ac.uns.ftn.nistagram.content.domain.core.story;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "stories_in_highlights")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HighlightedStory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @OneToOne
    private Story story;
    @OneToOne
    private StoryHighlight highlight;

}
