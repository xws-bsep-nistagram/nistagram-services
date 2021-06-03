package rs.ac.uns.ftn.nistagram.content.controller.dto.output;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;
import rs.ac.uns.ftn.nistagram.content.domain.locale.Location;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PostOverviewDTO {
    private String author;
    private String caption;
    private Location location;
    private LocalDateTime time;
    private List<Post.MediaLink> mediaUrls;
}
