package rs.ac.uns.ftn.nistagram.notification.messaging.payload.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostInteractionEventPayload extends NotificationEventPayload {

    private String target;

}
