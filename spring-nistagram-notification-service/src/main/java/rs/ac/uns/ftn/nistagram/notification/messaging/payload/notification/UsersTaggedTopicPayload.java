package rs.ac.uns.ftn.nistagram.notification.messaging.payload.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersTaggedTopicPayload extends NotificationTopicPayload {

    private List<String> targets;

}
