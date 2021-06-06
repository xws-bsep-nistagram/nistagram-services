package rs.ac.uns.ftn.nistagram.content.messaging.mappers;

import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.ContentTopicPayload;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.PayloadType;

public class ContentTopicPayloadMapper {
    public static ContentTopicPayload toPayload(Post post, PayloadType payloadType){
        return ContentTopicPayload
                .builder()
                .contentId(post.getId())
                .author(post.getAuthor())
                .time(post.getTime())
                .payloadType(payloadType)
                .build();
    }
}
