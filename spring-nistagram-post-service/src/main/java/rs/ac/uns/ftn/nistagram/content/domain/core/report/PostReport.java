package rs.ac.uns.ftn.nistagram.content.domain.core.report;

import lombok.*;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostReport extends BaseReport {
    @ManyToOne
    private Post reportedPost;
}
