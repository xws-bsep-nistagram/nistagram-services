package rs.ac.uns.ftn.nistagram.user.messaging.eventlistener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import rs.ac.uns.ftn.nistagram.user.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.user.messaging.event.UserBannedEvent;
import rs.ac.uns.ftn.nistagram.user.messaging.event.UserCreatedEvent;
import rs.ac.uns.ftn.nistagram.user.messaging.event.UserUpdatedEvent;
import rs.ac.uns.ftn.nistagram.user.messaging.util.Converter;

@Slf4j
@Component
@AllArgsConstructor
public class UserEventListener {

    private static final String ROUTING_KEY = "";
    private final RabbitTemplate rabbitTemplate;
    private final Converter converter;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCreateEvent(UserCreatedEvent event) {

        log.info("Publishing an {}, event: {}",
                RabbitMQConfig.USER_CREATED_EVENT_GRAPH_SERVICE, event);

        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_CREATED_EVENT_GRAPH_SERVICE,
                converter.toJSON(event));

    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onUpdateEvent(UserUpdatedEvent event) {

        log.info("Sending user updated event to {}, event: {}", RabbitMQConfig.USER_UPDATED_TOPIC, event);

        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_UPDATED_TOPIC, ROUTING_KEY, converter.toJSON(event));

    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onBanEvent(UserBannedEvent event) {

        log.info("Sending user banned event to {}, event: {}", RabbitMQConfig.USER_BANNED_TOPIC, event);

        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_BANNED_TOPIC, ROUTING_KEY, converter.toJSON(event));

    }

}
