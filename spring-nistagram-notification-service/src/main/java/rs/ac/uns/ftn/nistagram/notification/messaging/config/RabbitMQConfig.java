package rs.ac.uns.ftn.nistagram.notification.messaging.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String USER_BANNED_EVENT = "USER_BANNED_EVENT_NOTIFICATION_SERVICE";

    public static final String USERS_TAGGED_EVENT = "USERS_TAGGED_EVENT";
    public static final String POST_LIKED_EVENT = "POST_LIKED_EVENT";
    public static final String POST_DISLIKED_EVENT = "POST_DISLIKED_EVENT";
    public static final String POST_SHARED_EVENT = "POST_SHARED_EVENT";
    public static final String POST_COMMENTED_EVENT = "POST_COMMENTED_EVENT";

    public static final String FOLLOW_REQUESTED_NOTIFICATION_SERVICE = "follow.requested.notification-service";
    public static final String FOLLOW_ACCEPTED_NOTIFICATION_SERVICE = "follow.accepted.notification-service";
    public static final String NEW_FOLLOW_NOTIFICATION_SERVICE = "new.follow.notification-service";


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

}
