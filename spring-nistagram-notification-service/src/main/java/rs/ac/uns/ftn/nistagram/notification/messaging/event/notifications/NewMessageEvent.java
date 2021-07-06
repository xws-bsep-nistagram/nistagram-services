package rs.ac.uns.ftn.nistagram.notification.messaging.event.notifications;

import lombok.*;
import rs.ac.uns.ftn.nistagram.notification.messaging.payload.notification.NotificationEventPayload;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NewMessageEvent {

    private String transactionId;

    private NotificationEventPayload newMessageEventPayload;

}
