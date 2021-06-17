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

    public static final String USERS_TAGGED_NOTIFICATION_SERVICE = "user.tagged.notification-service";
    public static final String POST_LIKED_NOTIFICATION_SERVICE = "post.liked.notification-service";
    public static final String POST_COMMENTED_NOTIFICATION_SERVICE = "post.commented.notification-service";

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
    public Queue usersTaggedNotificationQueue(){
        return new Queue(USERS_TAGGED_NOTIFICATION_SERVICE);
    }
    @Bean
    public Queue postLikedNotificationQueue() {
        return new Queue(POST_LIKED_NOTIFICATION_SERVICE);
    }
    @Bean
    public Queue postCommentedNotificationQueue(){
        return new Queue(POST_COMMENTED_NOTIFICATION_SERVICE);
    }

}
