package rs.ac.uns.ftn.nistagram.notification.messaging.eventhandler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.notification.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.notification.messaging.event.UserBannedEvent;
import rs.ac.uns.ftn.nistagram.notification.messaging.payload.user.UserEventPayload;
import rs.ac.uns.ftn.nistagram.notification.messaging.util.Converter;
import rs.ac.uns.ftn.nistagram.notification.messaging.util.TransactionIdHolder;
import rs.ac.uns.ftn.nistagram.notification.services.NotificationService;

@Slf4j
@Component
@AllArgsConstructor
public class UserEventHandler {

    private final Converter converter;
    private final TransactionIdHolder transactionIdHolder;
    NotificationService notificationService;

    @RabbitListener(queues = {RabbitMQConfig.USER_BANNED_EVENT})
    public void handleUserCreated(@Payload String payload) {

        log.debug("Handling a created user event {}", payload);

        UserBannedEvent event = converter.toObject(payload, UserBannedEvent.class);

        transactionIdHolder.setCurrentTransactionId(event.getTransactionId());

        UserEventPayload eventPayload = event.getUserEventPayload();

        notificationService.handleUserBanned(eventPayload.getUsername());

    }

}
