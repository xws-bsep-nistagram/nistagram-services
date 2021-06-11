package rs.ac.uns.ftn.nistagram.content.controller.dto.output;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
/* To be packaged exclusively within a Post-representative object because it does not contain a Post ID */
public class CommentOverviewDTO {
    private String author;
    private String text;
    private LocalDateTime time;
}
