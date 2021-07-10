package rs.ac.uns.ftn.nistagram.content.messaging.payload.notification;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostCommentedEventPayload extends NotificationEventPayload {

    private String target;
    private String text;

    @Builder
    public PostCommentedEventPayload(long contentId, LocalDateTime time, String subject,
                                     String target, String text) {
        super(contentId, time, subject);
        this.target = target;
        this.text = text;
    }

}
