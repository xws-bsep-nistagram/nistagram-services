package rs.ac.uns.ftn.nistagram.content.messaging.event.notification;

import lombok.*;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.notification.PostInteractionEventPayload;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostSharedEvent {

    private String transactionId;

    private PostInteractionEventPayload postSharedEventPayload;

}
