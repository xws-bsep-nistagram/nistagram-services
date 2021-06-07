package rs.ac.uns.ftn.nistagram.user.graph.messaging.producers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.user.graph.controllers.payload.UserRelationshipRequest;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.config.RabbitMQConfig;

@Service
@AllArgsConstructor
@Slf4j
public class UserRelationsProducer {
    private final RabbitTemplate rabbitTemplate;

    public void publishUserFollowed(String subject, String target){
        log.info("Follow event from {} to {} published to a {}",
                subject, target,
                RabbitMQConfig.USER_FOLLOWED_FEED_SERVICE);
        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_FOLLOWED_FEED_SERVICE,
                new UserRelationshipRequest(subject, target));
    }

    public void publishUserUnfollowed(String subject, String target){
        log.info("Unfollow event from {} to {} published to a {}",
                subject, target,
                RabbitMQConfig.USER_UNFOLLOWED_FEED_SERVICE);
        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_UNFOLLOWED_FEED_SERVICE,
                new UserRelationshipRequest(subject, target));
    }
}
