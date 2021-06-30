package rs.ac.uns.ftn.nistagram.feed.messaging.mappers.user;


import rs.ac.uns.ftn.nistagram.feed.domain.user.User;
import rs.ac.uns.ftn.nistagram.feed.messaging.payload.user.UserEventPayload;

public class UserEventPayloadMapper {
    public static User toDomain(UserEventPayload payload) {
        return User
                .builder()
                .username(payload.getUsername())
                .build();
    }
}
