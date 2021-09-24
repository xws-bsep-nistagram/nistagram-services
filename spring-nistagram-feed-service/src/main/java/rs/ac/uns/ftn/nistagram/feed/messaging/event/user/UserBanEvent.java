package rs.ac.uns.ftn.nistagram.feed.messaging.event.user;

import lombok.*;
import rs.ac.uns.ftn.nistagram.feed.messaging.payload.user.UserEventPayload;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserBanEvent {

    private String transactionId;

    private String username;

}
