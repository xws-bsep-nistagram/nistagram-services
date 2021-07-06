package rs.ac.uns.ftn.nistagram.chat.http;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRelationshipResponse {
    private boolean following;

    public UserRelationshipResponse() {
    }

    public UserRelationshipResponse(boolean following) {
        this.following = following;
    }
}
