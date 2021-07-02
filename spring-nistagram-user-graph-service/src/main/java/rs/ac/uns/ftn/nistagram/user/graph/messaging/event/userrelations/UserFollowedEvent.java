package rs.ac.uns.ftn.nistagram.user.graph.messaging.event.userrelations;

import lombok.*;
import rs.ac.uns.ftn.nistagram.user.graph.controllers.payload.UserRelationshipRequest;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserFollowedEvent {

    private String transactionId;

    private UserRelationshipRequest userFollowedPayload;
}
