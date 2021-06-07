package rs.ac.uns.ftn.nistagram.user.controllers.dtos;

import lombok.Getter;
import rs.ac.uns.ftn.nistagram.user.domain.user.preferences.NotificationPreferences;

import javax.validation.constraints.NotNull;

@Getter
public class NotificationPreferencesUpdateDTO {

    @NotNull
    private NotificationPreferences.Content.Preference comment;
    @NotNull
    private NotificationPreferences.Content.Preference likes;
    @NotNull
    private NotificationPreferences.Content.Preference photosOfMe;
    @NotNull
    private boolean followerRequestNotificationEnabled;
    @NotNull
    private boolean followRequestAcceptedNotificationEnabled;
    @NotNull
    private boolean newFollowerNotificationEnabled;
    @NotNull
    private boolean messageRequestNotificationEnabled;
    @NotNull
    private boolean messageNotificationEnabled;

}
