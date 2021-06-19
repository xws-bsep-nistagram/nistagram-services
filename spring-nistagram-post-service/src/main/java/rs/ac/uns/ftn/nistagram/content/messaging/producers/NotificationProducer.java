package rs.ac.uns.ftn.nistagram.content.messaging.producers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.social.Comment;
import rs.ac.uns.ftn.nistagram.content.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.content.messaging.mappers.TopicPayloadMapper;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.notification.PostCommentedTopicPayload;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.notification.PostLikedTopicPayload;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.notification.UserTaggedTopicPayload;

@Service
@Slf4j
@AllArgsConstructor
public class NotificationProducer {
    private final RabbitTemplate rabbitTemplate;

    public void publishUserTagged(Post post) {
        log.info("User tagged event published to a {}", RabbitMQConfig.USERS_TAGGED_NOTIFICATION_SERVICE);
        UserTaggedTopicPayload payload = TopicPayloadMapper.toPayload(post);
        rabbitTemplate.convertAndSend(RabbitMQConfig.USERS_TAGGED_NOTIFICATION_SERVICE, payload);
    }

    public void publishPostLiked(Post post, String subject) {
        log.info("Post liked event published to a {}", RabbitMQConfig.POST_LIKED_NOTIFICATION_SERVICE);
        PostLikedTopicPayload payload = TopicPayloadMapper.toPayload(post, subject);
        rabbitTemplate.convertAndSend(RabbitMQConfig.POST_LIKED_NOTIFICATION_SERVICE, payload);
    }

    public void publishPostCommented(Post post, Comment comment) {
        log.info("Post commented event published to a {}", RabbitMQConfig.POST_COMMENTED_NOTIFICATION_SERVICE);
        PostCommentedTopicPayload payload = TopicPayloadMapper.toPayload(post, comment);
        rabbitTemplate.convertAndSend(RabbitMQConfig.POST_COMMENTED_NOTIFICATION_SERVICE, payload);
    }
}
