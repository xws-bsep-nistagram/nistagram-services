package rs.ac.uns.ftn.nistagram.notification.messaging.eventlistener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.notification.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.notification.messaging.payload.user.UserBannedReply;
import rs.ac.uns.ftn.nistagram.notification.messaging.util.Converter;

@Slf4j
@Component
@AllArgsConstructor
public class UserEventListener {

    private final RabbitTemplate rabbitTemplate;
    private final Converter converter;

    @Async
    @EventListener
    public void onUserBanFailedEvent(UserBannedReply reply){

        log.info("Publishing a reply to {}, reply: {}",
                RabbitMQConfig.USER_BAN_SAGA_REPLY_CHANNEL, reply);

        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_BAN_SAGA_REPLY_CHANNEL, converter.toJSON(reply));
    }
}
