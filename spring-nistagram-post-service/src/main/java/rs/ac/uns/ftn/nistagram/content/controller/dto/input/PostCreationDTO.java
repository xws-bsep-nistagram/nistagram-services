package rs.ac.uns.ftn.nistagram.content.controller.dto.input;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.social.Tag;
import rs.ac.uns.ftn.nistagram.content.domain.locale.Location;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class PostCreationDTO {
    private String author;
    @NotNull
    private String caption;
    private Location location;
    @NotEmpty
    private List<Post.MediaLink> mediaUrls;
    @NotNull
    private List<Tag> tags;
}
