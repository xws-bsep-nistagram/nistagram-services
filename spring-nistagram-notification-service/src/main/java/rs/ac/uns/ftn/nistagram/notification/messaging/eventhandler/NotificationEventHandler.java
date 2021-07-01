package rs.ac.uns.ftn.nistagram.notification.messaging.eventhandler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.notification.domain.NotificationType;
import rs.ac.uns.ftn.nistagram.notification.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.notification.messaging.event.content.*;
import rs.ac.uns.ftn.nistagram.notification.messaging.event.notifications.FollowAcceptedEvent;
import rs.ac.uns.ftn.nistagram.notification.messaging.event.notifications.FollowRequestedEvent;
import rs.ac.uns.ftn.nistagram.notification.messaging.event.notifications.NewFollowEvent;
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

        log.info("Handling a user tagged event {}", payload);

        UserTaggedEvent event = converter.toObject(payload, UserTaggedEvent.class);

        notificationService.handleTaggedUsers(EventPayloadMapper
                .toDomain(event.getUserTaggedEventPayload()));

    }

    @RabbitListener(queues = {RabbitMQConfig.POST_LIKED_EVENT})
    public void handlePostLiked(@Payload String payload) {

        log.info("Handling a post liked event {}", payload);

        PostLikedEvent event = converter.toObject(payload, PostLikedEvent.class);

        notificationService.handlePostLiked(EventPayloadMapper
                .toDomain(event.getPostLikedEventPayload(), NotificationType.NEW_LIKE));

    }

    @RabbitListener(queues = {RabbitMQConfig.POST_DISLIKED_EVENT})
    public void handlePostDisliked(@Payload String payload) {

        log.info("Handling a post disliked event {}", payload);

        PostDislikedEvent event = converter.toObject(payload, PostDislikedEvent.class);

        notificationService.handlePostDisliked(EventPayloadMapper
                .toDomain(event.getPostDislikedEventPayload(), NotificationType.NEW_DISLIKE));

    }

    @RabbitListener(queues = {RabbitMQConfig.POST_SHARED_EVENT})
    public void handlePostShared(@Payload String payload) {

        log.info("Handling a post shared event {}", payload);

        PostSharedEvent event = converter.toObject(payload, PostSharedEvent.class);

        notificationService.handlePostShared(EventPayloadMapper
                .toDomain(event.getPostSharedEventPayload(), NotificationType.NEW_SHARE));

    }

    @RabbitListener(queues = {RabbitMQConfig.POST_COMMENTED_EVENT})
    public void handlePostCommented(@Payload String payload) {

        log.info("Handling a post commented event {}", payload);

        PostCommentedEvent event = converter.toObject(payload, PostCommentedEvent.class);

        notificationService.handlePostComments(EventPayloadMapper
                .toDomain(event.getPostCommentedEventPayload()));

    }

    @RabbitListener(queues = {RabbitMQConfig.FOLLOW_REQUEST_EVENT})
    public void handleFollowRequested(@Payload String payload) {

        log.info("Handling a follow requested event {}", payload);

        FollowRequestedEvent event = converter.toObject(payload, FollowRequestedEvent.class);

        notificationService.handleFollowRequested(EventPayloadMapper
                .toDomain(event.getFollowRequestedPayload()));

    }

    @RabbitListener(queues = {RabbitMQConfig.FOLLOW_REQUEST_ACCEPTED_EVENT})
    public void handleFollowRequestAccepted(@Payload String payload) {

        log.info("Handling a follow request accepted event {}", payload);

        FollowAcceptedEvent event = converter.toObject(payload, FollowAcceptedEvent.class);

        notificationService.handleFollowAccepted(EventPayloadMapper
                .toDomain(event.getFollowAcceptedPayload()));

    }

    @RabbitListener(queues = {RabbitMQConfig.NEW_FOLLOW_EVENT})
    public void handleNewFollow(@Payload String payload) {

        log.info("Handling a new follow request event {}", payload);

        NewFollowEvent event = converter.toObject(payload, NewFollowEvent.class);

        notificationService.handleNewFollow(EventPayloadMapper
                .toDomain(event.getNewFollowPayload()));

    }

}
