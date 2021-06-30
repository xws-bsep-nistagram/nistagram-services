package rs.ac.uns.ftn.nistagram.feed.messaging.event.userrelations;

import lombok.*;
import rs.ac.uns.ftn.nistagram.feed.messaging.payload.user.relations.UserRelationshipRequest;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserFollowedEvent {

    private String transactionId;

    private UserRelationshipRequest userFollowedPayload;
}
