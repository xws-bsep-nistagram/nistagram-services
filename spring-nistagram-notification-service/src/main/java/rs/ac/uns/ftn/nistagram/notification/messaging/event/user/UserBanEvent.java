package rs.ac.uns.ftn.nistagram.notification.messaging.event.user;

import lombok.*;
import rs.ac.uns.ftn.nistagram.notification.messaging.payload.user.UserEventPayload;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserBanEvent {

    private String transactionId;

    private String username;

}
