package rs.ac.uns.ftn.nistagram.user.messaging.mappers;

import rs.ac.uns.ftn.nistagram.user.domain.user.User;
import rs.ac.uns.ftn.nistagram.user.messaging.payload.UserTopicPayload;

public class UserTopicPayloadMapper {
    public static UserTopicPayload toPayload(User user){
        return UserTopicPayload
                .builder()
                .username(user.getUsername())
                .administrativeData(user.getAdministrativeData())
                .privacyData(user.getPrivacyData())
                .notificationPreferences(user.getNotificationPreferences())
                .build();
    }
}
