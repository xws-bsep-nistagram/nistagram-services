package rs.ac.uns.ftn.nistagram.user.domain.user.preferences;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Data
@Embeddable
public class NotificationPreferences {

    @Embedded
    private Following following;
    @Embedded
    private Message message;
    @Embedded
    private Content content;

    public NotificationPreferences() {
        this.following = new Following();
        this.message = new Message();
        this.content = new Content();
    }

    @Getter
    @Setter
    @Embeddable
    public static class Content {

        private Preference comment;
        private Preference likes;
        private Preference dislikes;
        private Preference shares;
        private Preference photosOfMe;


        public Content() {
            this.comment = Preference.PEOPLE_I_FOLLOW;
            this.likes = Preference.PEOPLE_I_FOLLOW;
            this.dislikes = Preference.PEOPLE_I_FOLLOW;
            this.shares = Preference.PEOPLE_I_FOLLOW;
            this.photosOfMe = Preference.PEOPLE_I_FOLLOW;
        }

        public enum Preference {OFF, PEOPLE_I_FOLLOW, EVERYONE}

    }

    @Getter
    @Setter
    @Embeddable
    public static class Following {

        private boolean followerRequestNotificationEnabled;
        private boolean followRequestAcceptedNotificationEnabled;
        private boolean newFollowerNotificationEnabled;

        public Following() {
            this.followerRequestNotificationEnabled = true;
            this.followRequestAcceptedNotificationEnabled = true;
            this.newFollowerNotificationEnabled = true;
        }

    }

    @Getter
    @Setter
    @Embeddable
    public static class Message {

        private boolean messageRequestNotificationEnabled;
        private boolean messageNotificationEnabled;

        public Message() {
            this.messageRequestNotificationEnabled = true;
            this.messageNotificationEnabled = false;
        }

    }

}
