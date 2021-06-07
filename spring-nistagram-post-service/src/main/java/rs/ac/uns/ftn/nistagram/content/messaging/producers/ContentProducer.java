package rs.ac.uns.ftn.nistagram.content.messaging.producers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;
import rs.ac.uns.ftn.nistagram.content.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.content.messaging.mappers.PostTopicPayloadMapper;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.PostPayloadType;

@Service
@Slf4j
@AllArgsConstructor
public class ContentProducer {
    private final RabbitTemplate rabbitTemplate;

    public void publishPostCreated(Post post){
        log.info("Post created event published to a {}", RabbitMQConfig.POST_CREATED_FEED_SERVICE);
        rabbitTemplate.convertAndSend(RabbitMQConfig.POST_CREATED_FEED_SERVICE,
                PostTopicPayloadMapper.toPayload(post, PostPayloadType.POST_CREATED));
    }
    public void publishPostDeleted(Post post){
        log.info("Post deleted event published to a {}", RabbitMQConfig.POST_DELETED_FEED_SERVICE);
        rabbitTemplate.convertAndSend(RabbitMQConfig.POST_DELETED_FEED_SERVICE,
                PostTopicPayloadMapper.toPayload(post, PostPayloadType.POST_DELETED));
    }
}
