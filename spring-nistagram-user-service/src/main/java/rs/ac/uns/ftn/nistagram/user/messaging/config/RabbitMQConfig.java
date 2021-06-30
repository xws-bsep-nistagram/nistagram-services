package rs.ac.uns.ftn.nistagram.user.messaging.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String USER_CREATED_TOPIC = "USER_CREATED_TOPIC";
    public static final String USER_UPDATED_TOPIC = "USER_UPDATED_TOPIC";
    public static final String USER_BANNED_TOPIC = "USER_BANNED_TOPIC";

}
