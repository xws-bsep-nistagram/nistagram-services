package rs.ac.uns.ftn.nistagram.content.messaging.mappers.story;

import rs.ac.uns.ftn.nistagram.content.domain.core.story.Story;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.story.StoryPayloadType;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.story.StoryTopicPayload;

public class StoryTopicPayloadMapper {
    public static StoryTopicPayload toPayload(Story story, StoryPayloadType storyPayloadType){
        return StoryTopicPayload
                .builder()
                .contentId(story.getId())
                .author(story.getAuthor())
                .time(story.getTime())
                .closeFriends(story.isCloseFriends())
                .storyPayloadType(storyPayloadType)
                .build();
    }
}
