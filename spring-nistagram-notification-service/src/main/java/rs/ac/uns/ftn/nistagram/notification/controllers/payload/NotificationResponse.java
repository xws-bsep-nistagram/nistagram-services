package rs.ac.uns.ftn.nistagram.notification.controllers.payload;

import lombok.*;
import rs.ac.uns.ftn.nistagram.notification.domain.NotificationType;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private Long id;
    private Long contentId;
    private boolean seen;
    private LocalDateTime time;
    private String text;
    private NotificationType notificationType;

}
