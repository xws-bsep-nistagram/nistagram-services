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
    private NotificationPreferences dislikes;
    private NotificationPreferences shares;
    private NotificationPreferences photosOfMe;
    private boolean followerRequestNotificationEnabled;
    private boolean followRequestAcceptedNotificationEnabled;
    private boolean newFollowerNotificationEnabled;
    private boolean messageRequestNotificationEnabled;
    private boolean messageNotificationEnabled;


    public boolean commentNotificationEnabledForFollowers() {
        return this.comment.equals(NotificationPreferences.PEOPLE_I_FOLLOW);
    }

    public boolean commentNotificationEnabled() {
        return this.comment.equals(NotificationPreferences.EVERYONE);
    }

    public boolean likeNotificationEnabled() {
        return this.likes.equals(NotificationPreferences.EVERYONE);
    }

    public boolean likeNotificationEnabledForFollowers() {
        return this.likes.equals(NotificationPreferences.PEOPLE_I_FOLLOW);
    }

    public boolean dislikeNotificationEnabled() {
        return this.dislikes.equals(NotificationPreferences.EVERYONE);
    }

    public boolean dislikeNotificationEnabledForFollowers() {
        return this.dislikes.equals(NotificationPreferences.PEOPLE_I_FOLLOW);
    }

    public boolean shareNotificationEnabled() {
        return this.shares.equals(NotificationPreferences.EVERYONE);
    }

    public boolean shareNotificationEnabledForFollowers() {
        return this.shares.equals(NotificationPreferences.PEOPLE_I_FOLLOW);
    }

    public boolean tagNotificationEnabledForFollowers() {
        return this.photosOfMe.equals(NotificationPreferences.PEOPLE_I_FOLLOW);
    }

    public boolean tagNotificationEnabled() {
        return this.photosOfMe.equals(NotificationPreferences.EVERYONE);
    }


}
