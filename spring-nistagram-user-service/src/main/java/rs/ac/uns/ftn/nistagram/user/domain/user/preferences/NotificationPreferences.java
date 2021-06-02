package rs.ac.uns.ftn.nistagram.user.domain.user.preferences;

public class NotificationPreferences {
    private Following following;
    private Message message;
    private Content content;

    public static class Content {
        private Preference comment;
        private Preference likes;
        private Preference photosOfMe;

        public enum Preference { OFF, PEOPLE_I_FOLLOW, EVERYONE }
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
