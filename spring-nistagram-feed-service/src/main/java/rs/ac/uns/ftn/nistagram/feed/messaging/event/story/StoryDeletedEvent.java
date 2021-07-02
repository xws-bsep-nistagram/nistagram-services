package rs.ac.uns.ftn.nistagram.feed.messaging.event.story;

import lombok.*;
import rs.ac.uns.ftn.nistagram.feed.messaging.payload.story.StoryEventPayload;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StoryDeletedEvent {

    private String transactionId;

    private StoryEventPayload storyEventPayload;

}
