package rs.ac.uns.ftn.nistagram.content.messaging.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String USER_BANNED_POST_CHANNEL = "USER_BANNED_POST_CHANNEL";
    public static final String USER_BAN_SAGA_REPLY_CHANNEL = "USER_BAN_SAGA_REPLY_CHANNEL";

    public static final String POST_CREATED_EVENT = "POST_CREATED_EVENT";
    public static final String POST_DELETED_EVENT = "POST_DELETED_EVENT";
    public static final String STORY_CREATED_EVENT = "STORY_CREATED_EVENT";
    public static final String STORY_DELETED_EVENT = "STORY_DELETED_EVENT";

    public static final String USERS_TAGGED_EVENT = "USERS_TAGGED_EVENT";
    public static final String POST_LIKED_EVENT = "POST_LIKED_EVENT";
    public static final String POST_DISLIKED_EVENT = "POST_DISLIKED_EVENT";
    public static final String POST_SHARED_EVENT = "POST_SHARED_EVENT";
    public static final String POST_COMMENTED_EVENT = "POST_COMMENTED_EVENT";

}
