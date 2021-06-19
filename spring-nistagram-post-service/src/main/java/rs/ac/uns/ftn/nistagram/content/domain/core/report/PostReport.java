package rs.ac.uns.ftn.nistagram.content.domain.core.report;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PostReport extends BaseReport {
    @ManyToOne
    private Post reportedPost;

    public PostReport(BaseReport baseReport) {
        super(baseReport);
    }

    public void assignPost(Post reportedPost) {
        this.reportedPost = reportedPost;
    }


}
