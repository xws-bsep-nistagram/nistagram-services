package rs.ac.uns.ftn.nistagram.feed.messaging.payload.story;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StoryCreatedEvent {

    private String transactionId;

    private StoryEventPayload storyEventPayload;

}
