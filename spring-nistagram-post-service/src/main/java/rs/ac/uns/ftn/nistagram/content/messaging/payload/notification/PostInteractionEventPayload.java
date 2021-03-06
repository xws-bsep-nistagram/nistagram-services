package rs.ac.uns.ftn.nistagram.content.messaging.payload.notification;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostInteractionEventPayload extends NotificationEventPayload {

    private String target;

    @Builder
    public PostInteractionEventPayload(long contentId, LocalDateTime time, String subject, String target) {
        super(contentId, time, subject);
        this.target = target;
    }
}
