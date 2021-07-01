package rs.ac.uns.ftn.nistagram.content.messaging.event.content;

import lombok.*;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.story.StoryEventPayload;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StoryDeletedEvent {

    private String transactionId;

    private StoryEventPayload storyEventPayload;

}
