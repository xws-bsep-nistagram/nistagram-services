package rs.ac.uns.ftn.nistagram.feed.messaging.eventhandler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.feed.exceptions.EntityAlreadyExistsException;
import rs.ac.uns.ftn.nistagram.feed.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.feed.messaging.event.user.RegistrationFailedEvent;
import rs.ac.uns.ftn.nistagram.feed.messaging.event.user.UserBannedEvent;
import rs.ac.uns.ftn.nistagram.feed.messaging.event.user.UserCreatedEvent;
import rs.ac.uns.ftn.nistagram.feed.messaging.mappers.user.UserEventPayloadMapper;
import rs.ac.uns.ftn.nistagram.feed.messaging.util.Converter;
import rs.ac.uns.ftn.nistagram.feed.messaging.util.TransactionIdHolder;
import rs.ac.uns.ftn.nistagram.feed.services.UserService;

@Slf4j
@Component
@AllArgsConstructor
public class UserEventHandler {

    private final UserService userService;
    private final Converter converter;
    private final TransactionIdHolder transactionIdHolder;
    private final ApplicationEventPublisher publisher;

    @RabbitListener(queues = {RabbitMQConfig.USER_CREATED_EVENT_FEED_SERVICE})
    public void handleUserCreated(@Payload String payload) {

        log.info("Handling an user created event: {}", payload);

        UserCreatedEvent userCreatedEvent = converter.toObject(payload, UserCreatedEvent.class);

        transactionIdHolder.setCurrentTransactionId(userCreatedEvent.getTransactionId());

        try {
            userService.create(UserEventPayloadMapper.toDomain(userCreatedEvent.getUserEventPayload()));
        } catch (Exception e) {
            publishRegistrationFailed(userCreatedEvent.getUserEventPayload().getUsername());
        }
    }

    private void publishRegistrationFailed(String username) {

        RegistrationFailedEvent event = new RegistrationFailedEvent(transactionIdHolder.getCurrentTransactionId(),
                username);

        publisher.publishEvent(event);

    }

    @RabbitListener(queues = {RabbitMQConfig.USER_BANNED_EVENT})
    public void handleUserBanned(@Payload String payload) {

        log.info("Handling a banned user event {}", payload);

        UserBannedEvent event = converter.toObject(payload, UserBannedEvent.class);

        transactionIdHolder.setCurrentTransactionId(event.getTransactionId());

        userService.delete(UserEventPayloadMapper.toDomain(event.getUserEventPayload()));

    }

}
