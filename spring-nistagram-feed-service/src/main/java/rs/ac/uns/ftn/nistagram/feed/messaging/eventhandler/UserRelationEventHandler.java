package rs.ac.uns.ftn.nistagram.feed.messaging.eventhandler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.feed.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.feed.messaging.event.userrelations.UserFollowedEvent;
import rs.ac.uns.ftn.nistagram.feed.messaging.event.userrelations.UserMutedEvent;
import rs.ac.uns.ftn.nistagram.feed.messaging.event.userrelations.UserUnfollowedEvent;
import rs.ac.uns.ftn.nistagram.feed.messaging.event.userrelations.UserUnmutedEvent;
import rs.ac.uns.ftn.nistagram.feed.messaging.payload.user.relations.UserRelationshipRequest;
import rs.ac.uns.ftn.nistagram.feed.messaging.util.Converter;
import rs.ac.uns.ftn.nistagram.feed.services.FeedService;

@Slf4j
@Component
@AllArgsConstructor
public class UserRelationEventHandler {

    private final Converter converter;
    private final FeedService feedService;

    @RabbitListener(queues = {RabbitMQConfig.USER_FOLLOWED_EVENT})
    public void handleUserFollowed(@Payload String payload) {

        log.debug("Handling a user followed event {}", payload);

        UserFollowedEvent event = converter.toObject(payload, UserFollowedEvent.class);

        UserRelationshipRequest eventPayload = event.getUserFollowedPayload();

        feedService.addTargetsContent(eventPayload.getSubject(), eventPayload.getTarget());

    }

    @RabbitListener(queues = {RabbitMQConfig.USER_UNFOLLOWED_EVENT})
    public void handleUserUnfollowed(@Payload String payload) {

        log.debug("Handling a user unfollowed event {}", payload);

        UserUnfollowedEvent event = converter.toObject(payload, UserUnfollowedEvent.class);

        UserRelationshipRequest eventPayload = event.getUserUnfollowedPayload();

        feedService.removeTargetsContent(eventPayload.getSubject(), eventPayload.getTarget());

    }

    @RabbitListener(queues = {RabbitMQConfig.USER_MUTED_EVENT})
    public void handleUserMuted(@Payload String payload) {

        log.debug("Handling a user muted event {}", payload);

        UserMutedEvent event = converter.toObject(payload, UserMutedEvent.class);

        UserRelationshipRequest eventPayload = event.getUserMutedPayload();

        feedService.addTargetsContent(eventPayload.getSubject(), eventPayload.getTarget());

    }

    @RabbitListener(queues = {RabbitMQConfig.USER_UNMUTED_EVENT})
    public void handleUserUnmuted(@Payload String payload) {

        log.debug("Handling a user unmuted event {}", payload);

        UserUnmutedEvent event = converter.toObject(payload, UserUnmutedEvent.class);

        UserRelationshipRequest eventPayload = event.getUserUnmutedPayload();

        feedService.removeTargetsContent(eventPayload.getSubject(), eventPayload.getTarget());

    }

}
