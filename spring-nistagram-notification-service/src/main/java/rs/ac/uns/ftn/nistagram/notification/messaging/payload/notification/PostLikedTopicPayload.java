package rs.ac.uns.ftn.nistagram.notification.messaging.payload.notification;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostLikedTopicPayload extends NotificationTopicPayload{

    private String target;

}
