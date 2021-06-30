package rs.ac.uns.ftn.nistagram.notification.messaging.payload.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.notification.domain.NotificationType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRelationEventPayload extends NotificationEventPayload {

    private String target;
    private NotificationType notificationType;
}
