package rs.ac.uns.ftn.nistagram.content.messaging.event.content;

import lombok.*;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.post.PostEventPayload;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostCreatedEvent {

    private String transactionId;

    private PostEventPayload postEventPayload;

}
