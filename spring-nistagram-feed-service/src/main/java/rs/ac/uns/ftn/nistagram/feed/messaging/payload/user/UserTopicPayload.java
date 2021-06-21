package rs.ac.uns.ftn.nistagram.feed.messaging.payload.user;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTopicPayload {

    private String username;
    private PrivacyData privacyData;
    private AdministrativeData administrativeData;
    private NotificationPreferences notificationPreferences;

}
