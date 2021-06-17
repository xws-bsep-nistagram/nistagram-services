package rs.ac.uns.ftn.nistagram.notification.http.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPreferencesDTO {

    private NotificationPreferences comment;
    private NotificationPreferences likes;
    private NotificationPreferences photosOfMe;
    private boolean followerRequestNotificationEnabled;
    private boolean followRequestAcceptedNotificationEnabled;
    private boolean newFollowerNotificationEnabled;
    private boolean messageRequestNotificationEnabled;
    private boolean messageNotificationEnabled;

    public boolean commentNotificationDisabled() {
        return this.comment.equals(NotificationPreferences.OFF);
    }

    public boolean commentNotificationEnabledForFollowers() {
        return this.comment.equals(NotificationPreferences.PEOPLE_I_FOLLOW);
    }

    public boolean commentNotificationEnabled() {
        return this.comment.equals(NotificationPreferences.EVERYONE);
    }

    public boolean likeNotificationDisabled() {
        return this.likes.equals(NotificationPreferences.OFF);
    }

    public boolean likeNotificationEnabledForFollowers() {
        return this.likes.equals(NotificationPreferences.PEOPLE_I_FOLLOW);
    }

    public boolean likeNotificationEnabled() {
        return this.likes.equals(NotificationPreferences.EVERYONE);
    }

    public boolean tagNotificationDisabled() {
        return this.photosOfMe.equals(NotificationPreferences.OFF);
    }

    public boolean tagNotificationEnabledForFollowers() {
        return this.photosOfMe.equals(NotificationPreferences.PEOPLE_I_FOLLOW);
    }

    public boolean tagNotificationEnabled() {
        return this.photosOfMe.equals(NotificationPreferences.EVERYONE);
    }


}
