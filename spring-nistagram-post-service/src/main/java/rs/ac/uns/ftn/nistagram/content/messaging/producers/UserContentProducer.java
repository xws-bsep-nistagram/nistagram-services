package rs.ac.uns.ftn.nistagram.content.messaging.producers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;
import rs.ac.uns.ftn.nistagram.content.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.content.messaging.mappers.ContentTopicPayloadMapper;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.PayloadType;

@Service
@Slf4j
@AllArgsConstructor
public class UserContentProducer {
    private final RabbitTemplate rabbitTemplate;

    public void publishPostCreated(Post post){
        log.info("Post created event published to a {}", RabbitMQConfig.CONTENT_CREATED_FEED_SERVICE);
        rabbitTemplate.convertAndSend(RabbitMQConfig.CONTENT_CREATED_FEED_SERVICE,
                ContentTopicPayloadMapper.toPayload(post, PayloadType.POST_CREATED));
    }
    public void publishPostDeleted(Post post){
        log.info("Post deleted event published to a {}", RabbitMQConfig.CONTENT_CREATED_FEED_SERVICE);
        rabbitTemplate.convertAndSend(RabbitMQConfig.CONTENT_CREATED_FEED_SERVICE,
                ContentTopicPayloadMapper.toPayload(post, PayloadType.POST_DELETED));
    }
}
