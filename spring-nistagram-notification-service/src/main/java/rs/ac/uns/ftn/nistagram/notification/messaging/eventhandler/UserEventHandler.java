package rs.ac.uns.ftn.nistagram.notification.messaging.eventhandler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.notification.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.notification.messaging.event.user.UserBanEvent;
import rs.ac.uns.ftn.nistagram.notification.messaging.payload.user.UserBannedReply;
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
    private final ApplicationEventPublisher publisher;


    @RabbitListener(queues = {RabbitMQConfig.USER_BANNED_NOTIFICATION_CHANNEL})
    public void handleUserBanned(@Payload String payload) {

        log.info("Handling an user banned event: {}", payload);

        UserBanEvent event = converter.toObject(payload, UserBanEvent.class);

        transactionIdHolder.setCurrentTransactionId(event.getTransactionId());

        UserBannedReply reply = new UserBannedReply(RabbitMQConfig.USER_BANNED_NOTIFICATION_CHANNEL,
                event.getUsername());

        try{
            notificationService.handleUserBanned(event.getUsername());
            reply.setOperationSucceeded(true);
        }catch (Exception e){
            reply.setOperationSucceeded(false);
            log.warn("Banning an user with an username '{}' failed", event.getUsername());
        }

        publisher.publishEvent(reply);

    }

    @RabbitListener(queues = {RabbitMQConfig.USER_UNBAN_NOTIFICATION_CHANNEL})
    public void handleUserUnbanned(@Payload String payload){

        log.info("Handling an user unbanned event: {}", payload);

        UserBanEvent event = converter.toObject(payload, UserBanEvent.class);

        transactionIdHolder.setCurrentTransactionId(event.getTransactionId());

        notificationService.handleUserUnbanned(event.getUsername());

    }

}
