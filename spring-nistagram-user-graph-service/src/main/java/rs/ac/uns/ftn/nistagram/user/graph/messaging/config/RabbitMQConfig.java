package rs.ac.uns.ftn.nistagram.user.graph.messaging.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String USER_CREATED_EVENT_GRAPH_SERVICE = "USER_CREATED_EVENT_GRAPH_SERVICE";
    public static final String USER_CREATED_EVENT_FEED_SERVICE = "USER_CREATED_EVENT_FEED_SERVICE";
    public static final String REGISTRATION_FAILED_EVENT_USER_SERVICE = "REGISTRATION_FAILED_EVENT_USER_SERVICE";
    public static final String REGISTRATION_FAILED_EVENT_GRAPH_SERVICE = "REGISTRATION_FAILED_EVENT_GRAPH_SERVICE";
    public static final String USER_UPDATED_EVENT = "USER_UPDATED_EVENT_GRAPH_SERVICE";
    public static final String USER_BANNED_GRAPH_CHANNEL = "USER_BANNED_GRAPH_CHANNEL";
    public static final String USER_UNBAN_GRAPH_CHANNEL = "USER_UNBAN_GRAPH_CHANNEL";
    public static final String USER_BAN_SAGA_REPLY_CHANNEL = "USER_BAN_SAGA_REPLY_CHANNEL";


    public static final String USER_FOLLOWED_EVENT = "USER_FOLLOWED_EVENT";
    public static final String USER_UNFOLLOWED_EVENT = "USER_UNFOLLOWED_EVENT";
    public static final String USER_MUTED_EVENT = "USER_MUTED_EVENT";
    public static final String USER_UNMUTED_EVENT = "USER_UNMUTED_EVENT";

    public static final String FOLLOW_REQUEST_EVENT = "FOLLOW_REQUEST_EVENT";
    public static final String FOLLOW_REQUEST_ACCEPTED_EVENT = "FOLLOW_REQUEST_ACCEPTED_EVENT";
    public static final String NEW_FOLLOW_EVENT = "NEW_FOLLOW_EVENT";

}
