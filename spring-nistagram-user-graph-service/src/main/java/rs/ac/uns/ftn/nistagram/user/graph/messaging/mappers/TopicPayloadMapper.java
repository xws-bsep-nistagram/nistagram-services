package rs.ac.uns.ftn.nistagram.user.graph.messaging.mappers;


import rs.ac.uns.ftn.nistagram.user.graph.domain.ProfileType;
import rs.ac.uns.ftn.nistagram.user.graph.domain.User;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.payload.notification.NotificationTopicPayload;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.payload.notification.NotificationType;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.payload.user.UserTopicPayload;

import java.time.LocalDateTime;

public class TopicPayloadMapper {
    public static User toDomain(UserTopicPayload payload) {
        return User
                .builder()
                .username(payload.getUsername())
                .profileType(payload.getPrivacyData().isProfilePrivate()
                        ? ProfileType.PRIVATE : ProfileType.PUBLIC)
                .build();
    }

    public static NotificationTopicPayload toDomain(String subject, String target, NotificationType type) {
        return NotificationTopicPayload
                .builder()
                .subject(subject)
                .target(target)
                .time(LocalDateTime.now())
                .notificationType(type)
                .build();
    }
}
