package rs.ac.uns.ftn.nistagram.content.controller.dto.output;

import lombok.*;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.social.UserInteraction;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
/* To be packaged exclusively within a Post-representative object because it does not contain a Post ID */
public class UserInteractionOverviewDTO {
    private String author;
    private UserInteraction.Sentiment sentiment;
}
