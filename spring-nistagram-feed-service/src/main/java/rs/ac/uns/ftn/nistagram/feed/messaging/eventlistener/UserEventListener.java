package rs.ac.uns.ftn.nistagram.feed.messaging.eventlistener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import rs.ac.uns.ftn.nistagram.feed.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.feed.messaging.event.user.RegistrationFailedEvent;
import rs.ac.uns.ftn.nistagram.feed.messaging.util.Converter;

@Slf4j
@Component
@AllArgsConstructor
public class UserEventListener {

    private final RabbitTemplate rabbitTemplate;
    private final Converter converter;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void onRegistrationFailedEvent(RegistrationFailedEvent event) {

        log.info("Sending registration failed event to {}, event: {}", RabbitMQConfig.USER_REGISTRATION_FAILED_TOPIC, event);

        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_REGISTRATION_FAILED_TOPIC, converter.toJSON(event));

    }


}
