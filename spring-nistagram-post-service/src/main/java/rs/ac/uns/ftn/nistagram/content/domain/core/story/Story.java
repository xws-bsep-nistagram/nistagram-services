package rs.ac.uns.ftn.nistagram.content.domain.core.story;

import lombok.*;
import rs.ac.uns.ftn.nistagram.content.domain.core.UserContent;
import rs.ac.uns.ftn.nistagram.content.domain.core.report.StoryReport;

import javax.persistence.*;
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
}
