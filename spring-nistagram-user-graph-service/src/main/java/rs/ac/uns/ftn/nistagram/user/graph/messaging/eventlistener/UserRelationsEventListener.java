package rs.ac.uns.ftn.nistagram.user.graph.messaging.eventlistener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.event.userrelations.UserFollowedEvent;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.event.userrelations.UserMutedEvent;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.event.userrelations.UserUnfollowedEvent;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.event.userrelations.UserUnmutedEvent;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.util.Converter;

@Slf4j
@Component
@AllArgsConstructor
public class UserRelationsEventListener {

    private final RabbitTemplate rabbitTemplate;
    private final Converter converter;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onUserFollowedEvent(UserFollowedEvent event) {

        log.debug("Sending user followed event to {}, event: {}", RabbitMQConfig.USER_FOLLOWED_EVENT, event);

        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_FOLLOWED_EVENT, converter.toJSON(event));

    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onUserUnfollowedEvent(UserUnfollowedEvent event) {

        log.debug("Sending user unfollowed event to {}, event: {}", RabbitMQConfig.USER_UNFOLLOWED_EVENT, event);

        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_UNFOLLOWED_EVENT, converter.toJSON(event));

    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onUserMutedEvent(UserMutedEvent event) {

        log.debug("Sending user muted event to {}, event: {}", RabbitMQConfig.USER_MUTED_EVENT, event);

        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_MUTED_EVENT, converter.toJSON(event));

    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onUserUnmutedEvent(UserUnmutedEvent event) {

        log.debug("Sending user unmuted event to {}, event: {}", RabbitMQConfig.USER_UNMUTED_EVENT, event);

        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_UNMUTED_EVENT, converter.toJSON(event));

    }

}
