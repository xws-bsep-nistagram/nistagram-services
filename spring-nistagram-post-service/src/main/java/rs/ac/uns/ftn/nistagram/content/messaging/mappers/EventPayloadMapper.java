package rs.ac.uns.ftn.nistagram.content.messaging.mappers;

import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.social.Comment;
import rs.ac.uns.ftn.nistagram.content.domain.core.story.Story;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.notification.PostCommentedEventPayload;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.notification.PostInteractionEventPayload;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.notification.UserTaggedEventPayload;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.post.PostEventPayload;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.post.PostPayloadType;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.story.StoryEventPayload;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.story.StoryPayloadType;

import java.time.LocalDateTime;

public class EventPayloadMapper {
    public static PostEventPayload toPayload(Post post, PostPayloadType postPayloadType) {
        return PostEventPayload
                .builder()
                .contentId(post.getId())
                .author(post.getAuthor())
                .time(post.getTime())
                .postPayloadType(postPayloadType)
                .build();
    }

    public static StoryEventPayload toPayload(Story story, StoryPayloadType storyPayloadType) {
        return StoryEventPayload
                .builder()
                .contentId(story.getId())
                .author(story.getAuthor())
                .time(story.getTime())
                .closeFriends(story.isCloseFriends())
                .storyPayloadType(storyPayloadType)
                .build();
    }

    public static UserTaggedEventPayload toPayload(Post post) {
        UserTaggedEventPayload payload = UserTaggedEventPayload
                .builder()
                .contentId(post.getId())
                .time(post.getTime())
                .subject(post.getAuthor())
                .build();

        post.getTags().forEach(payload::addTarget);
        return payload;
    }

    public static PostInteractionEventPayload toPayload(Post post, String subject) {
        return PostInteractionEventPayload
                .builder()
                .contentId(post.getId())
                .subject(subject)
                .target(post.getAuthor())
                .time(post.getTime())
                .build();

    }

    public static PostInteractionEventPayload toPayload(Post post, LocalDateTime time, String subject) {
        return PostInteractionEventPayload
                .builder()
                .contentId(post.getId())
                .subject(subject)
                .target(post.getAuthor())
                .time(time)
                .build();

    }

    public static PostCommentedEventPayload toPayload(Post post, Comment comment) {
        return PostCommentedEventPayload
                .builder()
                .contentId(post.getId())
                .subject(comment.getAuthor())
                .target(post.getAuthor())
                .text(comment.getText())
                .time(post.getTime())
                .build();
    }

}
