package rs.ac.uns.ftn.nistagram.user.graph.messaging.eventlistener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.event.notifications.FollowAcceptedEvent;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.event.notifications.FollowRequestedEvent;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.event.notifications.NewFollowEvent;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.util.Converter;

@Slf4j
@Component
@AllArgsConstructor
public class NotificationEventListener {

    private final RabbitTemplate rabbitTemplate;
    private final Converter converter;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onFollowAcceptedEvent(FollowAcceptedEvent event) {

        log.info("Sending follow accepted event to {}, event: {}", RabbitMQConfig.FOLLOW_REQUEST_ACCEPTED_EVENT, event);

        rabbitTemplate.convertAndSend(RabbitMQConfig.FOLLOW_REQUEST_ACCEPTED_EVENT, converter.toJSON(event));

    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onFollowRequestedEvent(FollowRequestedEvent event) {

        log.info("Sending follow requested event to {}, event: {}", RabbitMQConfig.FOLLOW_REQUEST_EVENT, event);

        rabbitTemplate.convertAndSend(RabbitMQConfig.FOLLOW_REQUEST_EVENT, converter.toJSON(event));

    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onNewFollowEvent(NewFollowEvent event) {

        log.info("Sending new follow event to {}, event: {}", RabbitMQConfig.NEW_FOLLOW_EVENT, event);

        rabbitTemplate.convertAndSend(RabbitMQConfig.NEW_FOLLOW_EVENT, converter.toJSON(event));

    }

}
