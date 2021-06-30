package rs.ac.uns.ftn.nistagram.content.messaging.eventlistener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import rs.ac.uns.ftn.nistagram.content.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.content.messaging.event.notification.*;
import rs.ac.uns.ftn.nistagram.content.messaging.util.Converter;

@Slf4j
@Component
@AllArgsConstructor
public class NotificationEventListener {

    private final RabbitTemplate rabbitTemplate;
    private final Converter converter;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onUserTaggedEvent(UserTaggedEvent event) {

        log.info("Sending user tagged event to {}, event: {}", RabbitMQConfig.USERS_TAGGED_EVENT, event);

        rabbitTemplate.convertAndSend(RabbitMQConfig.USERS_TAGGED_EVENT, converter.toJSON(event));

    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onPostLikedEvent(PostLikedEvent event) {

        log.info("Sending post liked event to {}, event: {}", RabbitMQConfig.POST_LIKED_EVENT, event);

        rabbitTemplate.convertAndSend(RabbitMQConfig.POST_LIKED_EVENT, converter.toJSON(event));

    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onPostDislikedEvent(PostDislikedEvent event) {

        log.info("Sending post disliked event to {}, event: {}", RabbitMQConfig.POST_DISLIKED_EVENT, event);

        rabbitTemplate.convertAndSend(RabbitMQConfig.POST_DISLIKED_EVENT, converter.toJSON(event));

    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onPostSharedEvent(PostSharedEvent event) {

        log.info("Sending post shared event to {}, event: {}", RabbitMQConfig.POST_SHARED_EVENT, event);

        rabbitTemplate.convertAndSend(RabbitMQConfig.POST_SHARED_EVENT, converter.toJSON(event));

    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onPostCommentedEvent(PostCommentedEvent event) {

        log.info("Sending post commented event to {}, event: {}", RabbitMQConfig.POST_COMMENTED_EVENT, event);

        rabbitTemplate.convertAndSend(RabbitMQConfig.POST_COMMENTED_EVENT, converter.toJSON(event));

    }


}
