package rs.ac.uns.ftn.nistagram.user.graph.messaging.event.notifications;

import lombok.*;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.payload.notification.NotificationEventPayload;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NewFollowEvent {

    private String transactionId;

    private NotificationEventPayload newFollowPayload;

}
