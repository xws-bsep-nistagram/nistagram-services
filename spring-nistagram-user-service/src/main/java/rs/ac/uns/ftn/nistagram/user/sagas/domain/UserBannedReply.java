package rs.ac.uns.ftn.nistagram.user.sagas.domain;

import lombok.Builder;
import lombok.Data;
import rs.ac.uns.ftn.nistagram.user.messaging.config.RabbitMQConfig;

@Data
@Builder
public class UserBannedReply {
    
    private String channel;
    private String username;
    private boolean operationSucceeded;

    public boolean graphChannelBanSucceeded() {
        return operationSucceeded && channel.equals(RabbitMQConfig.USER_BANNED_GRAPH_CHANNEL);
    }

    public boolean graphChannelBanFailed() {
        return !operationSucceeded && channel.equals(RabbitMQConfig.USER_BANNED_GRAPH_CHANNEL);
    }

    public boolean feedChannelBanSucceeded(){
        return operationSucceeded && channel.equals(RabbitMQConfig.USER_BANNED_FEED_CHANNEL);
    }

    public boolean feedChannelBanFailed(){
        return !operationSucceeded && channel.equals(RabbitMQConfig.USER_BANNED_FEED_CHANNEL);
    }

    public boolean notificationChannelBanSucceeded(){
        return operationSucceeded && channel.equals(RabbitMQConfig.USER_BANNED_NOTIFICATION_CHANNEL);
    }

    public boolean notificationChannelBanFailed(){
        return !operationSucceeded && channel.equals(RabbitMQConfig.USER_BANNED_NOTIFICATION_CHANNEL);
    }


    public boolean postChannelBanFailed(){
        return !operationSucceeded && channel.equals(RabbitMQConfig.USER_BANNED_POST_CHANNEL);
    }
}
