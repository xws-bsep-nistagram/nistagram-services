package rs.ac.uns.ftn.nistagram.notification.controllers.mappers;

import rs.ac.uns.ftn.nistagram.notification.controllers.payload.NotificationResponse;
import rs.ac.uns.ftn.nistagram.notification.domain.Notification;

public class NotificationMapper {

    public static NotificationResponse toDto(Notification notification) {
        return NotificationResponse
                .builder()
                .notificationType(notification.getNotificationType())
                .subject(notification.getSubject())
                .target(notification.getTarget())
                .seen(notification.isSeen())
                .contentId(notification.getContentId())
                .time(notification.getTime())
                .text(notification.getText())
                .id(notification.getId())
                .build();
    }
}
