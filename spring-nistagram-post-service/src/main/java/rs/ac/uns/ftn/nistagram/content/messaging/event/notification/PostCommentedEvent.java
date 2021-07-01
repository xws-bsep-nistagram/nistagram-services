package rs.ac.uns.ftn.nistagram.content.messaging.event.notification;

import lombok.*;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.notification.PostCommentedEventPayload;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostCommentedEvent {

    private String transactionId;

    private PostCommentedEventPayload postCommentedEventPayload;

}
