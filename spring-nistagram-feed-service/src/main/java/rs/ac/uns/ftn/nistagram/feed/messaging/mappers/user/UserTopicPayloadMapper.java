package rs.ac.uns.ftn.nistagram.feed.messaging.mappers.user;


import rs.ac.uns.ftn.nistagram.feed.domain.user.User;
import rs.ac.uns.ftn.nistagram.feed.messaging.payload.user.UserTopicPayload;

public class UserTopicPayloadMapper {
    public static User toDomain(UserTopicPayload payload){
        return User
                .builder()
                .username(payload.getUsername())
                .build();
    }
}
