package rs.ac.uns.ftn.nistagram.user.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import rs.ac.uns.ftn.nistagram.user.domain.user.preferences.NotificationPreferences;

@AllArgsConstructor
@Getter
public class NotificationPreferencesViewDTO {

    private NotificationPreferences.Content.Preference comment;
    private NotificationPreferences.Content.Preference likes;
    private NotificationPreferences.Content.Preference photosOfMe;
    private boolean followerRequestNotificationEnabled;
    private boolean followRequestAcceptedNotificationEnabled;
    private boolean newFollowerNotificationEnabled;
    private boolean messageRequestNotificationEnabled;
    private boolean messageNotificationEnabled;

}