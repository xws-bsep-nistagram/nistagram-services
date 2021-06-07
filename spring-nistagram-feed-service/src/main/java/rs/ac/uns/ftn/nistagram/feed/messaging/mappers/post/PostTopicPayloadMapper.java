package rs.ac.uns.ftn.nistagram.feed.messaging.mappers.post;

import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.PostFeedEntry;
import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.StoryFeedEntry;
import rs.ac.uns.ftn.nistagram.feed.messaging.payload.post.PostTopicPayload;

public class PostTopicPayloadMapper {

    public static PostFeedEntry toPostFeedEntry(PostTopicPayload postTopicPayload){
        return new PostFeedEntry(postTopicPayload.getAuthor(),
                                postTopicPayload.getTime(),
                                postTopicPayload.getContentId());
    }
}
