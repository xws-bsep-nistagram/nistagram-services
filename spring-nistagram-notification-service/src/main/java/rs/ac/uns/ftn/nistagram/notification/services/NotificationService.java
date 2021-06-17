package rs.ac.uns.ftn.nistagram.notification.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.notification.domain.Notification;
import rs.ac.uns.ftn.nistagram.notification.exceptions.OperationNotPermittedException;
import rs.ac.uns.ftn.nistagram.notification.http.UserClient;
import rs.ac.uns.ftn.nistagram.notification.http.UserGraphClient;
import rs.ac.uns.ftn.nistagram.notification.http.payload.NotificationPreferencesDTO;
import rs.ac.uns.ftn.nistagram.notification.repositories.NotificationRepository;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserClient userClient;
    private final UserGraphClient userGraphClient;

    public void handleTaggedUsers(List<Notification> taggedUserNotifications) {
        taggedUserNotifications.forEach(this::handleTaggedUser);
    }

    public List<Notification> get(String username) {
        List<Notification> notifications = notificationRepository.findByUsername(username);
        if (notifications == null)
            return new ArrayList<>();
        return notifications;
    }

    public Notification hide(String username, Long notificationId) {
        log.info("Hiding notification with an id: {} for an user '{}'", notificationId, username);

        Notification notification = notificationRepository.getById(notificationId);
        if (!notification.getTarget().equals(username)) {
            log.warn("Cannot hide notification with an id: {} because user '{}' doesn't own it", notificationId, username);
            throw new OperationNotPermittedException("Cannot hide this notification");
        }

        log.info("Notification with an id: {} successfully hidden", notificationId);
        notificationRepository.delete(notification);
        return notification;
    }

    public void handlePostComments(Notification postCommentedNotification) {
        log.info("Handling post commented notification for an user '{}'", postCommentedNotification.getTarget());
        NotificationPreferencesDTO preferences = userClient.getNotificationPreferences(postCommentedNotification.getTarget());

        if (preferencesSatisfied(postCommentedNotification, preferences)) {
            notificationRepository.save(postCommentedNotification);
            log.info("Notification satisfies user preferences");
            return;
        }

        log.info("Notification doesn't satisfy user preferences");

    }

    public void handlePostLikes(Notification postLikedNotification) {
        log.info("Handling post liked notification for an user '{}'", postLikedNotification.getTarget());
        NotificationPreferencesDTO preferences = userClient.getNotificationPreferences(postLikedNotification.getTarget());

        if (preferencesSatisfied(postLikedNotification, preferences)) {
            notificationRepository.save(postLikedNotification);
            log.info("Notification satisfies user preferences");
            return;
        }
        log.info("Notification doesn't satisfy user preferences");
    }

    public void handleTaggedUser(Notification taggedUserNotification) {
        log.info("Handling tagged on post notification for an user '{}'", taggedUserNotification.getTarget());
        NotificationPreferencesDTO preferences = userClient.getNotificationPreferences(taggedUserNotification.getTarget());

        if (preferencesSatisfied(taggedUserNotification, preferences)) {
            notificationRepository.save(taggedUserNotification);
            log.info("Notification satisfies user preferences");
            return;
        }
        log.info("Notification doesn't satisfy user preferences");
    }

    private boolean preferencesSatisfied(Notification postCommentedNotification, NotificationPreferencesDTO preferences) {
        return preferences.commentNotificationEnabled() || followersPreferenceSatisfied(postCommentedNotification, preferences);
    }

    private boolean followersPreferenceSatisfied(Notification postCommentedNotification, NotificationPreferencesDTO preferences) {
        return preferences.commentNotificationEnabledForFollowers()
                && isFollowing(postCommentedNotification.getSubject(), postCommentedNotification.getTarget());
    }

    private boolean isFollowing(String subject, String target) {
        return userGraphClient.checkFollowing(subject, target).isFollowing();
    }


}
