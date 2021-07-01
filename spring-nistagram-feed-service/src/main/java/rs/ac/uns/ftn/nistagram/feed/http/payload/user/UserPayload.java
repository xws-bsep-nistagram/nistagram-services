package rs.ac.uns.ftn.nistagram.feed.http.payload.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserPayload {

    private String username;
    private ProfileType profileType;

    public UserPayload(String username) {
        this.username = username;
    }

}
