package rs.ac.uns.ftn.nistagram.user.graph.messaging.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String USER_CREATED_GRAPH_SERVICE = "user.created.graph-service";
    public static final String USER_UPDATED_GRAPH_SERVICE = "user.updated.graph-service";
    public static final String USER_FOLLOWED_FEED_SERVICE = "user.followed.feed-service";
    public static final String USER_UNFOLLOWED_FEED_SERVICE = "user.unfollowed.feed-service";
    public static final String USER_MUTED_FEED_SERVICE = "user.unfollowed.feed-service";
    public static final String USER_UNMUTED_FEED_SERVICE = "user.unfollowed.feed-service";
    public static final String FOLLOW_REQUESTED_NOTIFICATION_SERVICE = "follow.requested.notification-service";
    public static final String FOLLOW_ACCEPTED_NOTIFICATION_SERVICE = "follow.accepted.notification-service";
    public static final String NEW_FOLLOW_NOTIFICATION_SERVICE = "new.follow.notification-service";
    public static final String USER_BANNED_GRAPH_SERVICE = "user.banned.graph-service";

    @Bean
    public RabbitTemplate jsonRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonConverter());
        return template;
    }

    @Bean
    public MessageConverter jsonConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue userCreatedGraphQueue() {
        return new Queue(USER_CREATED_GRAPH_SERVICE);
    }

    @Bean
    public Queue userFollowedFeedQueue() {
        return new Queue(USER_FOLLOWED_FEED_SERVICE);
    }

    @Bean
    public Queue userMutedFeedQueue() {
        return new Queue(USER_MUTED_FEED_SERVICE);
    }

    @Bean
    public Queue userUnmutedFeedQueue() {
        return new Queue(USER_UNMUTED_FEED_SERVICE);
    }

    @Bean
    public Queue userUnfollowedFeedQueue() {
        return new Queue(USER_UNFOLLOWED_FEED_SERVICE);
    }

    @Bean
    public Queue userUpdatedGraphQueue() {
        return new Queue(USER_UPDATED_GRAPH_SERVICE);
    }

    @Bean
    public Queue followRequestedNotificationQueue() {
        return new Queue(FOLLOW_REQUESTED_NOTIFICATION_SERVICE);
    }

    @Bean
    public Queue followAcceptedNotificationQueue() {
        return new Queue(FOLLOW_ACCEPTED_NOTIFICATION_SERVICE);
    }

    @Bean
    public Queue newFollowNotificationQueue() {
        return new Queue(NEW_FOLLOW_NOTIFICATION_SERVICE);
    }

    @Bean
    public Queue userBannedGraphQueue() {
        return new Queue(USER_BANNED_GRAPH_SERVICE);
    }
}
