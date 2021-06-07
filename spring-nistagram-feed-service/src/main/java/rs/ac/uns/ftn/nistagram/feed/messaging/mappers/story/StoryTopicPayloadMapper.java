package rs.ac.uns.ftn.nistagram.feed.messaging.mappers.story;

import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.StoryFeedEntry;
import rs.ac.uns.ftn.nistagram.feed.messaging.payload.story.StoryTopicPayload;

public class StoryTopicPayloadMapper {
    public static StoryFeedEntry toStoryFeedEntry(StoryTopicPayload postTopicPayload){
        return new StoryFeedEntry(postTopicPayload.getAuthor(),
                postTopicPayload.getTime(),
                postTopicPayload.getContentId(), postTopicPayload.getCloseFriends());
    }
}
