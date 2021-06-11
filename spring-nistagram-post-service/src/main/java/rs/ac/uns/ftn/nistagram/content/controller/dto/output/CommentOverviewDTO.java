package rs.ac.uns.ftn.nistagram.content.controller.dto.output;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
/* To be packaged exclusively within a Post-representative object because it does not contain a Post ID */
public class CommentOverviewDTO {
    private String author;
    private String text;
    private LocalDateTime time;
}
