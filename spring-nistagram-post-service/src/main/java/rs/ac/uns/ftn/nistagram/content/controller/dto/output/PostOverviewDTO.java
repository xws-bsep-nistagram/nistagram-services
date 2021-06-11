package rs.ac.uns.ftn.nistagram.content.controller.dto.output;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.content.domain.core.UserContent;

import java.util.List;

@Getter
@Setter
public class PostOverviewDTO extends UserContent {
    private long id;

    private List<UserInteractionOverviewDTO> userInteractions;
    private List<CommentOverviewDTO> comments;

    private List<String> mediaUrls;
}
