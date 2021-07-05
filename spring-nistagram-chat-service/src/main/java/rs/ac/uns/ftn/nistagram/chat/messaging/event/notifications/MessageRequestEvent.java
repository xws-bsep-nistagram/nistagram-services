package rs.ac.uns.ftn.nistagram.chat.messaging.event.notifications;

import lombok.*;
import rs.ac.uns.ftn.nistagram.chat.messaging.event.payload.notification.NotificationEventPayload;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MessageRequestEvent {

    private String transactionId;

    private NotificationEventPayload messageRequestPayload;

}
