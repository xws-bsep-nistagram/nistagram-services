package rs.ac.uns.ftn.nistagram.feed.messaging.payload.event;

import lombok.*;
import rs.ac.uns.ftn.nistagram.feed.messaging.payload.user.UserEventPayload;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserCreatedEvent {

    private String transactionId;

    private UserEventPayload userEventPayload;

}
