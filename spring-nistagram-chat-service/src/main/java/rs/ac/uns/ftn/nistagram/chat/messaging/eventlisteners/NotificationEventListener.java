package rs.ac.uns.ftn.nistagram.chat.messaging.eventlisteners;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import rs.ac.uns.ftn.nistagram.chat.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.chat.messaging.event.notifications.MessageRequestEvent;
import rs.ac.uns.ftn.nistagram.chat.messaging.event.notifications.NewMessageEvent;
import rs.ac.uns.ftn.nistagram.chat.messaging.util.Converter;

@Slf4j
@Component
@AllArgsConstructor
public class NotificationEventListener {

    private final RabbitTemplate rabbitTemplate;
    private final Converter converter;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onMessageRequestEvent(MessageRequestEvent event) {

        log.info("Sending message request event to {}, event: {}", RabbitMQConfig.MESSAGE_REQUEST_RECEIVED_EVENT, event);

        rabbitTemplate.convertAndSend(RabbitMQConfig.MESSAGE_REQUEST_RECEIVED_EVENT, converter.toJSON(event));

    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onNewMessageEvent(NewMessageEvent event) {

        log.info("Sending new message event to {}, event: {}", RabbitMQConfig.MESSAGE_RECEIVED_EVENT, event);

        rabbitTemplate.convertAndSend(RabbitMQConfig.MESSAGE_RECEIVED_EVENT, converter.toJSON(event));

    }

}
