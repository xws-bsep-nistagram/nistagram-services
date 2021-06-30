package rs.ac.uns.ftn.nistagram.content.messaging.eventhandler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.content.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.content.messaging.event.UserBannedEvent;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.user.UserEventPayload;
import rs.ac.uns.ftn.nistagram.content.messaging.util.Converter;
import rs.ac.uns.ftn.nistagram.content.messaging.util.TransactionIdHolder;
import rs.ac.uns.ftn.nistagram.content.service.MessagingEventHandler;

@Slf4j
@Component
@AllArgsConstructor
public class UserEventHandler {

    private final Converter converter;
    private final TransactionIdHolder transactionIdHolder;
    private final MessagingEventHandler messagingEventHandler;

    @RabbitListener(queues = {RabbitMQConfig.USER_BANNED_EVENT})
    public void handleUserCreated(@Payload String payload) {

        log.debug("Handling a banned user event {}", payload);

        UserBannedEvent event = converter.toObject(payload, UserBannedEvent.class);

        transactionIdHolder.setCurrentTransactionId(event.getTransactionId());

        UserEventPayload eventPayload = event.getUserEventPayload();

        messagingEventHandler.handleUserBanned(eventPayload.getUsername());


    }

}
