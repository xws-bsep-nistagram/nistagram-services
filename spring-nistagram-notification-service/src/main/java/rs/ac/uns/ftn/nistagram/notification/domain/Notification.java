package rs.ac.uns.ftn.nistagram.notification.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Long contentId;
    private boolean seen;
    private boolean hidden;
    private LocalDateTime time;
    private String subject;
    private String target;
    private String text;
    private NotificationType notificationType;

    public void hide(){
        hidden = true;
    }

    public void unhide(){
        hidden = false;
    }
}
