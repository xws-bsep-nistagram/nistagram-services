package rs.ac.uns.ftn.nistagram.user.graph.messaging.event;

import lombok.*;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.payload.user.UserEventPayload;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserUpdatedEvent {

    private String transactionId;

    private UserEventPayload userEventPayload;

}
