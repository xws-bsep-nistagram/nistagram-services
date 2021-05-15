package rs.ac.uns.ftn.nistagram.notification.domain;

import java.time.Instant;

public class Notification {

    private Instant timestamp;
    private String username;
    private String causerUsername;
    private String content;
    private NotificationType notificationType;
    private Long postId;
    private String messageContent;

}
