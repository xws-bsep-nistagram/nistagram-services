package rs.ac.uns.ftn.nistagram.user.messaging.config;

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
    public static final String USER_CREATED_FEED_SERVICE = "user.created.feed-service";

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
    public Queue userCreatedFeedQueue() {
        return new Queue(USER_CREATED_FEED_SERVICE);
    }
}
