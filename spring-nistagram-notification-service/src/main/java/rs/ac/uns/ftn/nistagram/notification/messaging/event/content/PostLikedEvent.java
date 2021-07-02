package rs.ac.uns.ftn.nistagram.notification.messaging.event.content;

import lombok.*;
import rs.ac.uns.ftn.nistagram.notification.messaging.payload.notification.PostInteractionEventPayload;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostLikedEvent {

    private String transactionId;

    private PostInteractionEventPayload postLikedEventPayload;

}
