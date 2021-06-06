package rs.ac.uns.ftn.nistagram.user.graph.controllers.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFollowingResponse {
    private boolean following;

    public UserFollowingResponse(boolean following){
        this.following = following;
    }
}
