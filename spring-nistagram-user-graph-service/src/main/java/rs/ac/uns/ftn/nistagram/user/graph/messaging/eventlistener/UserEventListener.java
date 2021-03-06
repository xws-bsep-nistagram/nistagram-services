package rs.ac.uns.ftn.nistagram.user.graph.messaging.eventlistener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.event.user.RegistrationFailedEvent;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.event.user.UserCreatedEvent;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.util.Converter;

@Slf4j
@Component
@AllArgsConstructor
public class UserEventListener {

    private final RabbitTemplate rabbitTemplate;
    private final Converter converter;

    @Async
    @EventListener
    public void onRegistrationFailedEvent(RegistrationFailedEvent event) {

        log.info("Sending registration failed event to {}, event: {}", RabbitMQConfig.REGISTRATION_FAILED_EVENT_USER_SERVICE, event);

        rabbitTemplate.convertAndSend(RabbitMQConfig.REGISTRATION_FAILED_EVENT_USER_SERVICE, converter.toJSON(event));

    }

    @Async
    @EventListener
    public void onUserCreatedEvent(UserCreatedEvent event) {

        log.info("Sending a user created event to {}, event: {}", RabbitMQConfig.USER_CREATED_EVENT_FEED_SERVICE, event);

        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_CREATED_EVENT_FEED_SERVICE, converter.toJSON(event));

    }

}
