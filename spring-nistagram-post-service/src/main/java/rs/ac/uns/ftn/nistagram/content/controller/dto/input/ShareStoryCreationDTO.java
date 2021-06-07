package rs.ac.uns.ftn.nistagram.content.controller.dto.input;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShareStoryCreationDTO extends StoryCreationDTO {
    private long postId;
}
