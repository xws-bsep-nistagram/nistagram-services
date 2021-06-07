package rs.ac.uns.ftn.nistagram.content.controller.dto.output;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.content.domain.core.UserContent;

@Getter
@Setter
public class StoryOverviewDTO extends UserContent {
    private long id;
    private boolean closeFriends;

    private boolean isReshare;

    private String mediaUrl;
    private PostOverviewDTO post;
}

