package rs.ac.uns.ftn.nistagram.content.messaging.mappers;

import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.social.Comment;
import rs.ac.uns.ftn.nistagram.content.domain.core.story.Story;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.notification.PostCommentedTopicPayload;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.notification.PostLikedTopicPayload;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.notification.UserTaggedTopicPayload;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.post.PostTopicPayload;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.post.PostPayloadType;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.notification.NotificationTopicPayload;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.story.StoryPayloadType;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.story.StoryTopicPayload;

public class TopicPayloadMapper {
    public static PostTopicPayload toPayload(Post post, PostPayloadType postPayloadType){
        return PostTopicPayload
                .builder()
                .contentId(post.getId())
                .author(post.getAuthor())
                .time(post.getTime())
                .postPayloadType(postPayloadType)
                .build();
    }

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

    public static UserTaggedTopicPayload toPayload(Post post){
            UserTaggedTopicPayload payload = UserTaggedTopicPayload
                                                .builder()
                                                .contentId(post.getId())
                                                .time(post.getTime())
                                                .subject(post.getAuthor())
                                                .build();

            post.getTags().forEach(payload::addTarget);
            return payload;
    }

    public static PostLikedTopicPayload toPayload(Post post, String subject){
        return PostLikedTopicPayload
                .builder()
                .contentId(post.getId())
                .subject(subject)
                .target(post.getAuthor())
                .time(post.getTime())
                .build();

    }

    public static PostCommentedTopicPayload toPayload(Post post, Comment comment){
        return PostCommentedTopicPayload
                .builder()
                .contentId(post.getId())
                .subject(comment.getAuthor())
                .target(post.getAuthor())
                .text(comment.getText())
                .time(post.getTime())
                .build();
    }

}
