package rs.ac.uns.ftn.nistagram.user.messaging.mappers;

import rs.ac.uns.ftn.nistagram.user.domain.user.User;
import rs.ac.uns.ftn.nistagram.user.messaging.payload.UserEventPayload;

public class UserEventPayloadMapper {
    public static UserEventPayload toPayload(User user) {
        return UserEventPayload
                .builder()
                .username(user.getUsername())
                .administrativeData(user.getAdministrativeData())
                .privacyData(user.getPrivacyData())
                .notificationPreferences(user.getNotificationPreferences())
                .build();
    }
}
