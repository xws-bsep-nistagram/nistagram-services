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
    public static final String USER_CREATED_EVENT_FEED_SERVICE = "USER_CREATED_EVENT_FEED_SERVICE";
    public static final String REGISTRATION_FAILED_TOPIC = "REGISTRATION_FAILED_TOPIC";
    public static final String USER_BANNED_EVENT = "USER_BANNED_EVENT_FEED_SERVICE";

    public static final String POST_CREATED_EVENT = "POST_CREATED_EVENT";
    public static final String POST_DELETED_EVENT = "POST_DELETED_EVENT";
    public static final String STORY_CREATED_EVENT = "STORY_CREATED_EVENT";
    public static final String STORY_DELETED_EVENT = "STORY_DELETED_EVENT";

    public static final String USER_FOLLOWED_EVENT = "USER_FOLLOWED_EVENT";
    public static final String USER_UNFOLLOWED_EVENT = "USER_UNFOLLOWED_EVENT";
    public static final String USER_MUTED_EVENT = "USER_MUTED_EVENT";
    public static final String USER_UNMUTED_EVENT = "USER_UNMUTED_EVENT";
    public static final String TARGETED_CAMPAIGN_SHOW_FEED_SERVICE = "campaign.feed-service";

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
    public Queue campaignShowQueue() {
        return new Queue(TARGETED_CAMPAIGN_SHOW_FEED_SERVICE);
    }

}
