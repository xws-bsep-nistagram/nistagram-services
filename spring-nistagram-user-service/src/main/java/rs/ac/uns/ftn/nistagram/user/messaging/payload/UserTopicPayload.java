package rs.ac.uns.ftn.nistagram.user.messaging.payload;

import lombok.Builder;
import lombok.Data;
import rs.ac.uns.ftn.nistagram.user.domain.user.AdministrativeData;
import rs.ac.uns.ftn.nistagram.user.domain.user.PrivacyData;
import rs.ac.uns.ftn.nistagram.user.domain.user.preferences.NotificationPreferences;


@Data
@Builder
public class UserTopicPayload {

    private String username;
    private PrivacyData privacyData;
    private AdministrativeData administrativeData;
    private NotificationPreferences notificationPreferences;

}
