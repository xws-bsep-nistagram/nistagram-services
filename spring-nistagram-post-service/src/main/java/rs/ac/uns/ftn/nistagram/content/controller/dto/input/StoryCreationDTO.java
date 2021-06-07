package rs.ac.uns.ftn.nistagram.content.controller.dto.input;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.content.domain.locale.Location;

@Getter
@Setter
public class StoryCreationDTO {
    protected Location location;
    protected String author;
    protected boolean isCloseFriends;
    protected String caption;
}
