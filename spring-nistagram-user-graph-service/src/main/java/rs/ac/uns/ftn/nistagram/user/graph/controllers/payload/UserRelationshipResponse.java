package rs.ac.uns.ftn.nistagram.user.graph.controllers.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRelationshipResponse {
    private boolean following;

    public UserRelationshipResponse(boolean following){
        this.following = following;
    }
}
