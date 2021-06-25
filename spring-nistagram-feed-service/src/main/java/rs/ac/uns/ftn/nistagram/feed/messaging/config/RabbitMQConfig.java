package rs.ac.uns.ftn.nistagram.feed.messaging.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String USER_CREATED_FEED_SERVICE = "user.created.feed-service";
    public static final String USER_BANNED_FEED_SERVICE = "user.banned.feed-service";
    public static final String POST_CREATED_FEED_SERVICE = "post.created.feed-service";
    public static final String POST_DELETED_FEED_SERVICE = "post.deleted.feed-service";
    public static final String STORY_CREATED_FEED_SERVICE = "story.created.feed-service";
    public static final String STORY_DELETED_FEED_SERVICE = "story.deleted.feed-service";
    public static final String USER_FOLLOWED_FEED_SERVICE = "user.followed.feed-service";
    public static final String USER_UNFOLLOWED_FEED_SERVICE = "user.unfollowed.feed-service";
    public static final String USER_MUTED_FEED_SERVICE = "user.unfollowed.feed-service";
    public static final String USER_UNMUTED_FEED_SERVICE = "user.unfollowed.feed-service";

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
    public Queue userCreatedFeedQueue() {
        return new Queue(USER_CREATED_FEED_SERVICE);
    }

    @Bean
    public Queue postCreatedFeedQueue() {
        return new Queue(POST_CREATED_FEED_SERVICE);
    }

    @Bean
    public Queue postDeletedFeedQueue() {
        return new Queue(POST_DELETED_FEED_SERVICE);
    }

    @Bean
    public Queue storyCreatedFeedQueue() {
        return new Queue(STORY_CREATED_FEED_SERVICE);
    }

    @Bean
    public Queue storyDeletedFeedQueue() {
        return new Queue(STORY_DELETED_FEED_SERVICE);
    }

    @Bean
    public Queue userFollowedFeedQueue() {
        return new Queue(USER_FOLLOWED_FEED_SERVICE);
    }

    @Bean
    public Queue userUnfollowedFeedQueue() {
        return new Queue(USER_UNFOLLOWED_FEED_SERVICE);
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
    public Queue userBannedFeedQueue() {
        return new Queue(USER_BANNED_FEED_SERVICE);
    }
}
