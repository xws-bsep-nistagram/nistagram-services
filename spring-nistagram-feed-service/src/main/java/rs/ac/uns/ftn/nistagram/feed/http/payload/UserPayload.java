package rs.ac.uns.ftn.nistagram.feed.http.payload;

import lombok.Data;

@Data
public class UserPayload {

    private String username;
    private ProfileType profileType;

}
