package rs.ac.uns.ftn.nistagram.content.controller.dto.input;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.social.HashTag;
import rs.ac.uns.ftn.nistagram.content.domain.locale.Location;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class PostCreationDTO {
    @NotNull
    private String author;
    @NotNull
    private String caption;
    @NotNull
    private Location location;
    @NotEmpty
    private List<Post.MediaLink> mediaUrls;
    @NotNull
    private List<HashTag> hashTags;
}