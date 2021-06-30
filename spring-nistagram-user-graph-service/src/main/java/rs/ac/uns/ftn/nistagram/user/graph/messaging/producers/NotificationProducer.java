package rs.ac.uns.ftn.nistagram.user.graph.messaging.producers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.mappers.EventPayloadMapper;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.payload.notification.NotificationType;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationProducer {
    private final RabbitTemplate rabbitTemplate;

    public void publishFollowRequested(String subject, String target) {
        log.info("Follow request event published to a {}",
                RabbitMQConfig.FOLLOW_REQUESTED_NOTIFICATION_SERVICE);
        rabbitTemplate.convertAndSend(RabbitMQConfig.FOLLOW_REQUESTED_NOTIFICATION_SERVICE,
                EventPayloadMapper.toDomain(subject, target, NotificationType.NEW_FOLLOW_REQUEST));
    }

    public void publishFollowAccepted(String subject, String target) {
        log.info("Follow accepted event published to a {}",
                RabbitMQConfig.FOLLOW_ACCEPTED_NOTIFICATION_SERVICE);
        rabbitTemplate.convertAndSend(RabbitMQConfig.FOLLOW_ACCEPTED_NOTIFICATION_SERVICE,
                EventPayloadMapper.toDomain(target, subject, NotificationType.FOLLOW_REQUEST_ACCEPTED));
    }

    public void publishNewFollow(String subject, String target) {
        log.info("New follower event published to a {}",
                RabbitMQConfig.NEW_FOLLOW_NOTIFICATION_SERVICE);
        rabbitTemplate.convertAndSend(RabbitMQConfig.NEW_FOLLOW_NOTIFICATION_SERVICE,
                EventPayloadMapper.toDomain(subject, target, NotificationType.NEW_FOLLOWER));
    }


}
