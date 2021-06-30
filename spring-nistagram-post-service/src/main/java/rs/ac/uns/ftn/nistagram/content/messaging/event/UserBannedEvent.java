package rs.ac.uns.ftn.nistagram.content.messaging.event;

import lombok.*;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.user.UserEventPayload;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserBannedEvent {

    private String transactionId;

    private UserEventPayload userEventPayload;

}
