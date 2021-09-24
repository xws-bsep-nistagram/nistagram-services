package rs.ac.uns.ftn.nistagram.content.messaging.eventhandler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.content.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.content.messaging.event.user.UserBanEvent;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.user.UserBannedReply;
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
    private final ApplicationEventPublisher publisher;


    @RabbitListener(queues = {RabbitMQConfig.USER_BANNED_POST_CHANNEL})
    public void handleUserCreated(@Payload String payload) {

        log.info("Handling an user banned event: {}", payload);

        UserBanEvent event = converter.toObject(payload, UserBanEvent.class);

        transactionIdHolder.setCurrentTransactionId(event.getTransactionId());

        UserBannedReply reply = new UserBannedReply(RabbitMQConfig.USER_BANNED_POST_CHANNEL,
                event.getUsername());

        try{
            messagingEventHandler.handleUserBanned(event.getUsername());
            reply.setOperationSucceeded(true);
        }catch (Exception e){
            reply.setOperationSucceeded(false);
            log.warn("Banning an user with an username '{}' failed", event.getUsername());
        }

        publisher.publishEvent(reply);

    }

}
