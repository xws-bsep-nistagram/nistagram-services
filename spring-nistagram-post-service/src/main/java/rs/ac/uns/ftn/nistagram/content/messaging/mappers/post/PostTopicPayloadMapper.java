package rs.ac.uns.ftn.nistagram.content.messaging.mappers.post;

import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.post.PostTopicPayload;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.post.PostPayloadType;

public class PostTopicPayloadMapper {
    public static PostTopicPayload toPayload(Post post, PostPayloadType postPayloadType){
        return PostTopicPayload
                .builder()
                .contentId(post.getId())
                .author(post.getAuthor())
                .time(post.getTime())
                .postPayloadType(postPayloadType)
                .build();
    }
}
