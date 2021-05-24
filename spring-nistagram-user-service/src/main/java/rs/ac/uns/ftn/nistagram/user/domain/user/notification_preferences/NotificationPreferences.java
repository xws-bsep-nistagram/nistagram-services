package rs.ac.uns.ftn.nistagram.user.domain.user.notification_preferences;

import rs.ac.uns.ftn.nistagram.user.domain.user.notification_preferences.enums.ContentNotificationPreference;

public class NotificationPreferences {
    private Following following;
    private Message message;
    private Content content;

    public static class Content {
        private ContentNotificationPreference comment;
        private ContentNotificationPreference likes;
        private ContentNotificationPreference photosOfMe;
    }

    public static class Following {
        private boolean followerRequestNotificationEnabled;
        private boolean followRequestAcceptedNotificationEnabled;
        private boolean newFollowerNotificationEnabled;
    }

    public static class Message {
        private boolean messageRequestNotificationEnabled;
        private boolean messageNotificationEnabled;
    }
}
