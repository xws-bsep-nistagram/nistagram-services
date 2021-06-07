package rs.ac.uns.ftn.nistagram.feed.messaging.mappers.content;

import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.PostFeedEntry;
import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.StoryFeedEntry;
import rs.ac.uns.ftn.nistagram.feed.messaging.payload.content.PostTopicPayload;

public class PostTopicPayloadMapper {

    public static PostFeedEntry toPostFeedEntry(PostTopicPayload postTopicPayload){
        return new PostFeedEntry(postTopicPayload.getAuthor(),
                                postTopicPayload.getTime(),
                                postTopicPayload.getContentId());
    }
    public static StoryFeedEntry toStoryFeedEntry(PostTopicPayload postTopicPayload){
        return new StoryFeedEntry(postTopicPayload.getAuthor(),
                                postTopicPayload.getTime(),
                                postTopicPayload.getContentId());
    }

}
