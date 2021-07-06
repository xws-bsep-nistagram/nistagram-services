package rs.ac.uns.ftn.nistagram.chat.messaging.mappers;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.chat.domain.Message;
import rs.ac.uns.ftn.nistagram.chat.messaging.event.payload.notification.NotificationEventPayload;
import rs.ac.uns.ftn.nistagram.chat.messaging.event.payload.notification.NotificationType;

@Component
public class EventPayloadMapper {
    public NotificationEventPayload toPayload(Message message, NotificationType notificationType) {
        return NotificationEventPayload
                .builder()
                .time(message.getTime())
                .subject(message.getSender())
                .target(message.getReceiver())
                .notificationType(notificationType)
                .build();
    }
}
