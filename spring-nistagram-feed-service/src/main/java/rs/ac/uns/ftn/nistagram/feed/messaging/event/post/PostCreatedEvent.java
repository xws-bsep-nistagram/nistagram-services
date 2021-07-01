package rs.ac.uns.ftn.nistagram.feed.messaging.event.post;

import lombok.*;
import rs.ac.uns.ftn.nistagram.feed.messaging.payload.post.PostEventPayload;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostCreatedEvent {

    private String transactionId;

    private PostEventPayload postEventPayload;

}
