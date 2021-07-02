package rs.ac.uns.ftn.nistagram.content.messaging.eventlistener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import rs.ac.uns.ftn.nistagram.content.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.content.messaging.event.content.PostCreatedEvent;
import rs.ac.uns.ftn.nistagram.content.messaging.event.content.PostDeletedEvent;
import rs.ac.uns.ftn.nistagram.content.messaging.event.content.StoryCreatedEvent;
import rs.ac.uns.ftn.nistagram.content.messaging.event.content.StoryDeletedEvent;
import rs.ac.uns.ftn.nistagram.content.messaging.util.Converter;

@Slf4j
@Component
@AllArgsConstructor
public class ContentEventListener {

    private final RabbitTemplate rabbitTemplate;
    private final Converter converter;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onPostCreateEvent(PostCreatedEvent event) {

        log.info("Sending post created event to {}, event: {}", RabbitMQConfig.POST_CREATED_EVENT, event);

        rabbitTemplate.convertAndSend(RabbitMQConfig.POST_CREATED_EVENT, converter.toJSON(event));

    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onPostDeleteEvent(PostDeletedEvent event) {

        log.info("Sending post deleted event to {}, event: {}", RabbitMQConfig.POST_DELETED_EVENT, event);

        rabbitTemplate.convertAndSend(RabbitMQConfig.POST_DELETED_EVENT, converter.toJSON(event));

    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onStoryCreateEvent(StoryCreatedEvent event) {

        log.info("Sending story created event to {}, event: {}", RabbitMQConfig.STORY_CREATED_EVENT, event);

        rabbitTemplate.convertAndSend(RabbitMQConfig.STORY_CREATED_EVENT, converter.toJSON(event));

    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onStoryDeleteEvent(StoryDeletedEvent event) {

        log.info("Sending story deleted event to {}, event: {}", RabbitMQConfig.STORY_DELETED_EVENT, event);

        rabbitTemplate.convertAndSend(RabbitMQConfig.STORY_DELETED_EVENT, converter.toJSON(event));

    }


}
