package rs.ac.uns.ftn.nistagram.notification.messaging.eventhandler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.notification.domain.NotificationType;
import rs.ac.uns.ftn.nistagram.notification.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.notification.messaging.event.notification.*;
import rs.ac.uns.ftn.nistagram.notification.messaging.mappers.EventPayloadMapper;
import rs.ac.uns.ftn.nistagram.notification.messaging.util.Converter;
import rs.ac.uns.ftn.nistagram.notification.services.NotificationService;

@Slf4j
@Component
@AllArgsConstructor
public class NotificationEventHandler {

    private final Converter converter;
    private final NotificationService notificationService;

    @RabbitListener(queues = {RabbitMQConfig.USERS_TAGGED_EVENT})
    public void handleUserTagged(@Payload String payload) {

        log.debug("Handling a user tagged event {}", payload);

        UserTaggedEvent event = converter.toObject(payload, UserTaggedEvent.class);

        notificationService.handleTaggedUsers(EventPayloadMapper
                .toDomain(event.getUserTaggedEventPayload()));

    }

    @RabbitListener(queues = {RabbitMQConfig.POST_LIKED_EVENT})
    public void handlePostLiked(@Payload String payload) {

        log.debug("Handling a post liked event {}", payload);

        PostLikedEvent event = converter.toObject(payload, PostLikedEvent.class);

        notificationService.handlePostLiked(EventPayloadMapper
                .toDomain(event.getPostLikedEventPayload(), NotificationType.NEW_LIKE));

    }

    @RabbitListener(queues = {RabbitMQConfig.POST_DISLIKED_EVENT})
    public void handlePostDisliked(@Payload String payload) {

        log.debug("Handling a post disliked event {}", payload);

        PostDislikedEvent event = converter.toObject(payload, PostDislikedEvent.class);

        notificationService.handlePostDisliked(EventPayloadMapper
                .toDomain(event.getPostDislikedEventPayload(), NotificationType.NEW_DISLIKE));

    }

    @RabbitListener(queues = {RabbitMQConfig.POST_SHARED_EVENT})
    public void handlePostShared(@Payload String payload) {

        log.debug("Handling a post shared event {}", payload);

        PostSharedEvent event = converter.toObject(payload, PostSharedEvent.class);

        notificationService.handlePostShared(EventPayloadMapper
                .toDomain(event.getPostSharedEventPayload(), NotificationType.NEW_SHARE));

    }

    @RabbitListener(queues = {RabbitMQConfig.POST_COMMENTED_EVENT})
    public void handlePostCommented(@Payload String payload) {

        log.debug("Handling a post commented event {}", payload);

        PostCommentedEvent event = converter.toObject(payload, PostCommentedEvent.class);

        notificationService.handlePostComments(EventPayloadMapper
                .toDomain(event.getPostCommentedEventPayload()));

    }

}
