package rs.ac.uns.ftn.nistagram.notification.messaging.payload.notification;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostCommentedTopicPayload extends NotificationTopicPayload{

    private String target;
    private String text;

}
