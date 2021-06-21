package rs.ac.uns.ftn.nistagram.content.domain.core.report;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.content.domain.core.story.Story;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoryReport extends BaseReport {
    @ManyToOne
    private Story reportedStory;

    public StoryReport(BaseReport baseReport) {
        super(baseReport);
    }

    public void assignStory(Story reportedStory) {
        this.reportedStory = reportedStory;
    }

}
