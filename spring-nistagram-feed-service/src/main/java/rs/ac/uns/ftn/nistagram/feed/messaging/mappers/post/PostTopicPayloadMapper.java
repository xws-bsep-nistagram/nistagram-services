package rs.ac.uns.ftn.nistagram.feed.messaging.mappers.post;

import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.PostFeedEntry;
import rs.ac.uns.ftn.nistagram.feed.messaging.payload.post.PostEventPayload;

public class PostTopicPayloadMapper {

    public static PostFeedEntry toPostFeedEntry(PostEventPayload postEventPayload) {
        return new PostFeedEntry(postEventPayload.getAuthor(),
                postEventPayload.getTime(),
                postEventPayload.getContentId());
    }
}
