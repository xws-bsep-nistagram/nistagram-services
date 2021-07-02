package rs.ac.uns.ftn.nistagram.user.messaging.event;

import lombok.*;
import rs.ac.uns.ftn.nistagram.user.messaging.payload.UserEventPayload;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserCreatedEvent {

    private String transactionId;

    private UserEventPayload userEventPayload;

}
