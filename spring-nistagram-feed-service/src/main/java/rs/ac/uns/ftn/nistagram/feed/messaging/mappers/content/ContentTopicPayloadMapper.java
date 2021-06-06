package rs.ac.uns.ftn.nistagram.feed.messaging.mappers.content;

import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.PostFeedEntry;
import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.StoryFeedEntry;
import rs.ac.uns.ftn.nistagram.feed.messaging.payload.content.ContentTopicPayload;

public class ContentTopicPayloadMapper {

    public static PostFeedEntry toPostFeedEntry(ContentTopicPayload contentTopicPayload){
        return new PostFeedEntry(contentTopicPayload.getAuthor(),
                                contentTopicPayload.getTime(),
                                contentTopicPayload.getContentId());
    }
    public static StoryFeedEntry toStoryFeedEntry(ContentTopicPayload contentTopicPayload){
        return new StoryFeedEntry(contentTopicPayload.getAuthor(),
                                contentTopicPayload.getTime(),
                                contentTopicPayload.getContentId());
    }

}
