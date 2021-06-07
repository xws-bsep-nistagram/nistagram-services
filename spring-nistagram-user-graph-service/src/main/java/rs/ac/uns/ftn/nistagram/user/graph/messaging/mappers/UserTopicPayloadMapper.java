package rs.ac.uns.ftn.nistagram.user.graph.messaging.mappers;


import rs.ac.uns.ftn.nistagram.user.graph.domain.ProfileType;
import rs.ac.uns.ftn.nistagram.user.graph.domain.User;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.payload.user.UserTopicPayload;

public class UserTopicPayloadMapper {
    public static User toDomain(UserTopicPayload payload){
        return User
                .builder()
                .username(payload.getUsername())
                .profileType(payload.getPrivacyData().isProfilePrivate()
                        ? ProfileType.PRIVATE : ProfileType.PUBLIC)
                .build();
    }
}
