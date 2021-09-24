package rs.ac.uns.ftn.nistagram.user.messaging.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String USER_CREATED_EVENT_GRAPH_SERVICE = "USER_CREATED_EVENT_GRAPH_SERVICE";
    public static final String REGISTRATION_FAILED_EVENT = "REGISTRATION_FAILED_EVENT_USER_SERVICE";
    public static final String USER_UPDATED_TOPIC = "USER_UPDATED_TOPIC";
    public static final String USER_BANNED_GRAPH_CHANNEL = "USER_BANNED_GRAPH_CHANNEL";
    public static final String USER_UNBAN_GRAPH_CHANNEL = "USER_UNBAN_GRAPH_CHANNEL";
    public static final String USER_BANNED_FEED_CHANNEL = "USER_BANNED_FEED_CHANNEL";
    public static final String USER_UNBAN_FEED_CHANNEL = "USER_UNBAN_FEED_CHANNEL";
    public static final String USER_BANNED_NOTIFICATION_CHANNEL = "USER_BANNED_NOTIFICATION_CHANNEL";
    public static final String USER_UNBAN_NOTIFICATION_CHANNEL = "USER_UNBAN_NOTIFICATION_CHANNEL";
    public static final String USER_BANNED_POST_CHANNEL = "USER_BANNED_POST_CHANNEL";
    public static final String USER_BAN_SAGA_REPLY_CHANNEL = "USER_BAN_SAGA_REPLY_CHANNEL";

}
