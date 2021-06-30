package rs.ac.uns.ftn.nistagram.feed.messaging.payload.post;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostCreatedEvent {

    private String transactionId;

    private PostEventPayload postEventPayload;

}
