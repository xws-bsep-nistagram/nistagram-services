package rs.ac.uns.ftn.nistagram.feed.messaging.payload.user.relations;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRelationshipRequest {

    private String subject;
    private String target;

}
