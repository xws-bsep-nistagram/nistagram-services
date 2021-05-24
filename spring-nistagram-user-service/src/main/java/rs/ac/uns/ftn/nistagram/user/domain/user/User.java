package rs.ac.uns.ftn.nistagram.user.domain.user;

import rs.ac.uns.ftn.nistagram.user.domain.user.notification_preferences.NotificationPreferences;

public class User {
    private PersonalData personalData;
    private Credentials credentials;
    private PrivacyData privacyData;
    private AdministrativeData administrativeData;
    private NotificationPreferences notificationPreferences;
}
