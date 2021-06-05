package rs.ac.uns.ftn.nistagram.content.controller.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CommentCreationDTO {
    private long postId;
    @NotEmpty
    private String text;
    private String author;
}
