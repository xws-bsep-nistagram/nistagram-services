package rs.ac.uns.ftn.nistagram.user.graph.messaging.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificationPreferences {

    private Following following;
    private Message message;
    private Content content;


    @Getter
    @Setter
    @NoArgsConstructor
    public static class Content {

        private Preference comment;
        private Preference likes;
        private Preference photosOfMe;

        public enum Preference { OFF, PEOPLE_I_FOLLOW, EVERYONE }

    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Following {

        private boolean followerRequestNotificationEnabled;
        private boolean followRequestAcceptedNotificationEnabled;
        private boolean newFollowerNotificationEnabled;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Message {

        private boolean messageRequestNotificationEnabled;
        private boolean messageNotificationEnabled;

    }

}
