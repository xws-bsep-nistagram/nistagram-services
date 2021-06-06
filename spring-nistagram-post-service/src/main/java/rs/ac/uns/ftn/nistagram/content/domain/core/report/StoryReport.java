package rs.ac.uns.ftn.nistagram.content.domain.core.report;


import lombok.*;
import rs.ac.uns.ftn.nistagram.content.domain.core.story.Story;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoryReport extends BaseReport {
    @ManyToOne
    private Story reportedStory;
}

