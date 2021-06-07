package rs.ac.uns.ftn.nistagram.content.controller.dto.input;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MediaStoryCreationDTO extends StoryCreationDTO {
    @NotEmpty
    private String mediaUrl;
}
