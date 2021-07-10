package rs.ac.uns.ftn.nistagram.content.messaging.event.notification;

import lombok.*;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.notification.UserTaggedEventPayload;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserTaggedEvent {

    private String transactionId;

    private UserTaggedEventPayload userTaggedEventPayload;

}
