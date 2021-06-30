package rs.ac.uns.ftn.nistagram.notification.messaging.event.notification;

import lombok.*;
import rs.ac.uns.ftn.nistagram.notification.messaging.payload.notification.UserTaggedEventPayload;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserTaggedEvent {

    private String transactionId;

    private UserTaggedEventPayload userTaggedEventPayload;

}
