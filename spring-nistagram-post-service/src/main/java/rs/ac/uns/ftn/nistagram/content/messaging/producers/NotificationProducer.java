package rs.ac.uns.ftn.nistagram.content.messaging.producers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.social.Comment;
import rs.ac.uns.ftn.nistagram.content.domain.core.story.ShareStory;
import rs.ac.uns.ftn.nistagram.content.domain.core.story.Story;
import rs.ac.uns.ftn.nistagram.content.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.content.messaging.mappers.EventPayloadMapper;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.notification.PostCommentedTopicPayload;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.notification.PostInteractionTopicPayload;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.notification.UserTaggedTopicPayload;
import rs.ac.uns.ftn.nistagram.content.repository.post.PostRepository;

@Service
@Slf4j
@AllArgsConstructor
public class NotificationProducer {
    private final RabbitTemplate rabbitTemplate;
    private final PostRepository postRepository;

    public void publishUserTagged(Post post) {
        log.info("User tagged event published to a {}", RabbitMQConfig.USERS_TAGGED_NOTIFICATION_SERVICE);
        UserTaggedTopicPayload payload = EventPayloadMapper.toPayload(post);
        rabbitTemplate.convertAndSend(RabbitMQConfig.USERS_TAGGED_NOTIFICATION_SERVICE, payload);
    }

    public void publishPostLiked(Post post, String subject) {
        log.info("Post liked event published to a {}", RabbitMQConfig.POST_LIKED_NOTIFICATION_SERVICE);
        PostInteractionTopicPayload payload = EventPayloadMapper.toPayload(post, subject);
        rabbitTemplate.convertAndSend(RabbitMQConfig.POST_LIKED_NOTIFICATION_SERVICE, payload);
    }

    public void publishPostDisliked(Post post, String subject) {
        log.info("Post disliked event published to a {}", RabbitMQConfig.POST_DISLIKED_NOTIFICATION_SERVICE);
        PostInteractionTopicPayload payload = EventPayloadMapper.toPayload(post, subject);
        rabbitTemplate.convertAndSend(RabbitMQConfig.POST_DISLIKED_NOTIFICATION_SERVICE, payload);
    }

    public void publishPostShared(Story story) {
        log.info("Post shared event published to a {}", RabbitMQConfig.POST_SHARED_NOTIFICATION_SERVICE);

        ShareStory shareStory = (ShareStory) story;

        //When creating a share story, shared post is faked by setting a post id,
        //so I had to manually fetch it here.
        Post sharedPost = postRepository.getById(shareStory.getSharedPost().getId());
        PostInteractionTopicPayload payload = EventPayloadMapper.toPayload(sharedPost, shareStory.getTime(), shareStory.getAuthor());
        rabbitTemplate.convertAndSend(RabbitMQConfig.POST_SHARED_NOTIFICATION_SERVICE, payload);
    }

    public void publishPostCommented(Post post, Comment comment) {
        log.info("Post commented event published to a {}", RabbitMQConfig.POST_COMMENTED_NOTIFICATION_SERVICE);
        PostCommentedTopicPayload payload = EventPayloadMapper.toPayload(post, comment);
        rabbitTemplate.convertAndSend(RabbitMQConfig.POST_COMMENTED_NOTIFICATION_SERVICE, payload);
    }
}
