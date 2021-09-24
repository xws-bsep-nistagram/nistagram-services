package rs.ac.uns.ftn.nistagram.user.graph.messaging.eventhandler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.event.user.RegistrationFailedEvent;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.event.user.UserBanEvent;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.event.user.UserCreatedEvent;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.event.user.UserUpdatedEvent;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.mappers.EventPayloadMapper;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.payload.user.UserBannedReply;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.util.Converter;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.util.TransactionIdHolder;
import rs.ac.uns.ftn.nistagram.user.graph.services.UserService;

@Slf4j
@Component
@AllArgsConstructor
public class UserEventHandler {

    private final UserService userService;
    private final Converter converter;
    private final TransactionIdHolder transactionIdHolder;
    private final ApplicationEventPublisher publisher;

    @RabbitListener(queues = {RabbitMQConfig.USER_CREATED_EVENT_GRAPH_SERVICE})
    public void handleUserCreated(@Payload String payload) {

        log.info("Handling an user created event: {}", payload);

        UserCreatedEvent event = converter.toObject(payload, UserCreatedEvent.class);

        transactionIdHolder.setCurrentTransactionId(event.getTransactionId());

        try {
            userService.create(EventPayloadMapper.toDomain(event.getUserEventPayload()));
            publishRegistrationSucceeded(event);
        } catch (Exception e) {
            publishRegistrationFailed(event.getUserEventPayload().getUsername());
        }

    }

    private void publishRegistrationSucceeded(UserCreatedEvent event) {
        publisher.publishEvent(event);
    }

    private void publishRegistrationFailed(String username) {

        RegistrationFailedEvent event = new RegistrationFailedEvent(transactionIdHolder.getCurrentTransactionId(),
                username);

        publisher.publishEvent(event);

    }


    @RabbitListener(queues = {RabbitMQConfig.REGISTRATION_FAILED_EVENT_GRAPH_SERVICE})
    public void handleRegistrationFailed(@Payload String payload) {

        log.info("Handling a registration failed event: {}", payload);

        RegistrationFailedEvent event = converter.toObject(payload, RegistrationFailedEvent.class);

        userService.delete(event.getUsername());

    }


    @RabbitListener(queues = {RabbitMQConfig.USER_UPDATED_EVENT})
    public void handleUserUpdated(@Payload String payload) {

        log.info("Handling a user created event: {}", payload);

        UserUpdatedEvent event = converter.toObject(payload, UserUpdatedEvent.class);

        transactionIdHolder.setCurrentTransactionId(event.getTransactionId());

        userService.update(EventPayloadMapper.toDomain(event.getUserEventPayload()));

    }

    @RabbitListener(queues = {RabbitMQConfig.USER_BANNED_GRAPH_CHANNEL})
    public void handleUserBanned(@Payload String payload) {

        log.info("Handling an user banned event: {}", payload);

        UserBanEvent event = converter.toObject(payload, UserBanEvent.class);

        transactionIdHolder.setCurrentTransactionId(event.getTransactionId());

        UserBannedReply reply = new UserBannedReply(RabbitMQConfig.USER_BANNED_GRAPH_CHANNEL,
                event.getUsername());

        try{
            userService.ban(event.getUsername());
            reply.setOperationSucceeded(true);
        }catch (Exception e){
            reply.setOperationSucceeded(false);
            log.warn("Banning an user with an username '{}' failed", event.getUsername());
        }

        publisher.publishEvent(reply);

    }

    @RabbitListener(queues = {RabbitMQConfig.USER_UNBAN_GRAPH_CHANNEL})
    public void handleUserUnbanned(@Payload String payload){

        log.info("Handling an user unbanned event: {}", payload);

        UserBanEvent event = converter.toObject(payload, UserBanEvent.class);

        transactionIdHolder.setCurrentTransactionId(event.getTransactionId());

        userService.unban(event.getUsername());

    }


}
