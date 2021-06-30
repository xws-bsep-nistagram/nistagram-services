package rs.ac.uns.ftn.nistagram.feed.messaging.payload.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserBannedEvent {

    private String transactionId;

    private UserEventPayload userEventPayload;

}
