package rs.ac.uns.ftn.nistagram.content.messaging.payload.notification;

import lombok.*;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.social.Tag;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserTaggedEventPayload extends NotificationEventPayload {

    private List<String> targets;

    @Builder
    public UserTaggedEventPayload(long contentId, LocalDateTime time, String subject) {
        super(contentId, time, subject);
    }

    public void addTarget(Tag tag) {
        if (targets == null)
            targets = new ArrayList<>();
        targets.add(tag.getTag());
    }
}
