package rs.ac.uns.ftn.nistagram.user.graph.messaging.event.user;

import lombok.*;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.payload.user.UserEventPayload;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserCreatedEvent {

    private String transactionId;

    private UserEventPayload userEventPayload;

}
