package rs.ac.uns.ftn.nistagram.notification.messaging.mappers;

import rs.ac.uns.ftn.nistagram.notification.domain.Notification;
import rs.ac.uns.ftn.nistagram.notification.domain.NotificationType;
import rs.ac.uns.ftn.nistagram.notification.messaging.payload.notification.NotificationEventPayload;
import rs.ac.uns.ftn.nistagram.notification.messaging.payload.notification.PostCommentedEventPayload;
import rs.ac.uns.ftn.nistagram.notification.messaging.payload.notification.PostInteractionEventPayload;
import rs.ac.uns.ftn.nistagram.notification.messaging.payload.notification.UserTaggedEventPayload;

import java.util.ArrayList;
import java.util.List;

public class EventPayloadMapper {

    private static final String USER_TAGGED_MESSAGE = "User %s tagged you on his photo.";
    private static final String NEW_LIKE_MESSAGE = "User %s liked your photo.";
    private static final String NEW_DISLIKE_MESSAGE = "User %s disliked your photo.";
    private static final String NEW_SHARE_MESSAGE = "User %s shared your photo.";
    private static final String POST_COMMENTED_MESSAGE = "User %s commented '%s' on your photo.";
    private static final String FOLLOW_REQUESTED_MESSAGE = "User %s requested to follow you";
    private static final String FOLLOW_ACCEPTED_MESSAGE = "User %s accepted your follow request";
    private static final String NEW_FOLLOW_MESSAGE = "User %s is now following you.";
    private static final String NEW_REQUEST_MESSAGE = "User %s wants to chat with you.";
    private static final String NEW_MESSAGE = "User %s sent you a message.";


    public static List<Notification> toDomain(UserTaggedEventPayload payload) {
        List<Notification> notifications = new ArrayList<>();
        payload.getTargets().forEach(target -> notifications.add(Notification
                .builder()
                .time(payload.getTime())
                .target(target)
                .subject(payload.getSubject())
                .seen(false)
                .contentId(payload.getContentId())
                .notificationType(NotificationType.USER_TAGGED)
                .text(String.format(USER_TAGGED_MESSAGE, payload.getSubject()))
                .build()
        ));
        return notifications;
    }

    public static Notification toDomain(PostInteractionEventPayload payload, NotificationType type) {
        return Notification
                .builder()
                .time(payload.getTime())
                .target(payload.getTarget())
                .subject(payload.getSubject())
                .seen(false)
                .contentId(payload.getContentId())
                .notificationType(type)
                .text(formatUserNotificationMessage(type,
                        payload.getSubject()))
                .build();
    }

    public static Notification toDomain(PostCommentedEventPayload payload) {
        return Notification
                .builder()
                .time(payload.getTime())
                .target(payload.getTarget())
                .subject(payload.getSubject())
                .seen(false)
                .contentId(payload.getContentId())
                .notificationType(NotificationType.NEW_COMMENT)
                .text(String.format(POST_COMMENTED_MESSAGE, payload.getSubject(), payload.getText()))
                .build();
    }

    public static Notification toDomain(NotificationEventPayload payload) {
        return Notification
                .builder()
                .time(payload.getTime())
                .target(payload.getTarget())
                .subject(payload.getSubject())
                .notificationType(payload.getNotificationType())
                .text(formatUserNotificationMessage(payload.getNotificationType(),
                        payload.getSubject()))
                .build();
    }

    private static String formatUserNotificationMessage(NotificationType type, String subject) {
        switch (type) {
            case NEW_FOLLOWER:
                return String.format(NEW_FOLLOW_MESSAGE, subject);
            case FOLLOW_REQUEST_ACCEPTED:
                return String.format(FOLLOW_ACCEPTED_MESSAGE, subject);
            case NEW_FOLLOW_REQUEST:
                return String.format(FOLLOW_REQUESTED_MESSAGE, subject);
            case NEW_LIKE:
                return String.format(NEW_LIKE_MESSAGE, subject);
            case NEW_DISLIKE:
                return String.format(NEW_DISLIKE_MESSAGE, subject);
            case NEW_SHARE:
                return String.format(NEW_SHARE_MESSAGE, subject);
            case NEW_MESSAGE_REQUEST:
                return String.format(NEW_REQUEST_MESSAGE, subject);
            case NEW_MESSAGE:
                return String.format(NEW_MESSAGE, subject);
        }
        return "";
    }

}
