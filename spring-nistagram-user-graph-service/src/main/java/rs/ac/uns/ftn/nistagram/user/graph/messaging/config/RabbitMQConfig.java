package rs.ac.uns.ftn.nistagram.user.graph.messaging.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String USER_CREATED_EVENT = "USER_CREATED_EVENT_GRAPH_SERVICE";
    public static final String USER_UPDATED_EVENT = "USER_UPDATED_EVENT_GRAPH_SERVICE";
    public static final String USER_BANNED_EVENT = "USER_BANNED_EVENT_GRAPH_SERVICE";

    public static final String USER_FOLLOWED_EVENT = "USER_FOLLOWED_EVENT";
    public static final String USER_UNFOLLOWED_EVENT = "USER_UNFOLLOWED_EVENT";
    public static final String USER_MUTED_EVENT = "USER_MUTED_EVENT";
    public static final String USER_UNMUTED_EVENT = "USER_UNMUTED_EVENT";

    public static final String FOLLOW_REQUEST_EVENT = "FOLLOW_REQUEST_EVENT";
    public static final String FOLLOW_REQUEST_ACCEPTED_EVENT = "FOLLOW_REQUEST_ACCEPTED_EVENT";
    public static final String NEW_FOLLOW_EVENT = "NEW_FOLLOW_EVENT";

}
