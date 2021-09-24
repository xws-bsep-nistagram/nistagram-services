package rs.ac.uns.ftn.nistagram.notification.messaging.payload.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
@AllArgsConstructor
public class UserBannedReply {
    
    private String channel;
    private String username;
    private boolean operationSucceeded;

    public UserBannedReply(String channel, String username){
        this.channel = channel;
        this.username = username;
    }

}
