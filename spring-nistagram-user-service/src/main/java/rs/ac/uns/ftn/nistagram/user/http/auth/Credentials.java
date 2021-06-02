package rs.ac.uns.ftn.nistagram.user.http.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Credentials {

    private String username;
    private String password;
    private String email;

}
