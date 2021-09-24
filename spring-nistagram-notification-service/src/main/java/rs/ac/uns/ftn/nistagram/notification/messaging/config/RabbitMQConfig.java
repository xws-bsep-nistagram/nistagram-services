package rs.ac.uns.ftn.nistagram.notification.messaging.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String USER_BANNED_NOTIFICATION_CHANNEL = "USER_BANNED_NOTIFICATION_CHANNEL";
    public static final String USER_UNBAN_NOTIFICATION_CHANNEL = "USER_UNBAN_NOTIFICATION_CHANNEL";
    public static final String USER_BAN_SAGA_REPLY_CHANNEL = "USER_BAN_SAGA_REPLY_CHANNEL";


    public static final String USERS_TAGGED_EVENT = "USERS_TAGGED_EVENT";
    public static final String POST_LIKED_EVENT = "POST_LIKED_EVENT";
    public static final String POST_DISLIKED_EVENT = "POST_DISLIKED_EVENT";
    public static final String POST_SHARED_EVENT = "POST_SHARED_EVENT";
    public static final String POST_COMMENTED_EVENT = "POST_COMMENTED_EVENT";

    public static final String FOLLOW_REQUEST_EVENT = "FOLLOW_REQUEST_EVENT";
    public static final String FOLLOW_REQUEST_ACCEPTED_EVENT = "FOLLOW_REQUEST_ACCEPTED_EVENT";
    public static final String NEW_FOLLOW_EVENT = "NEW_FOLLOW_EVENT";

    public static final String MESSAGE_REQUEST_RECEIVED_EVENT = "MESSAGE_REQUEST_RECEIVED_EVENT";
    public static final String MESSAGE_RECEIVED_EVENT = "MESSAGE_RECEIVED_EVENT";

}
