package rs.ac.uns.ftn.nistagram.content.domain.core.story;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.content.domain.core.UserContent;
import rs.ac.uns.ftn.nistagram.content.domain.core.report.StoryReport;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stories")
@Getter
@Setter
public abstract class Story extends UserContent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected long id;
    protected boolean closeFriends;

    @OneToMany(mappedBy = "reportedStory", cascade = CascadeType.REMOVE)
    protected List<StoryReport> storyReports;

    @OneToMany(mappedBy = "story", cascade = CascadeType.REMOVE)
    private List<HighlightedStory> highlights;

    @Override
    public boolean equals(Object obj) {
        try {
            return ((Story) obj).getId() == this.id;
        } catch (Exception e) {
            return false;
        }
    }

    public void addReport(StoryReport storyReport) {
        if (storyReports == null) storyReports = new ArrayList<>();
        storyReports.add(storyReport);
    }
}
