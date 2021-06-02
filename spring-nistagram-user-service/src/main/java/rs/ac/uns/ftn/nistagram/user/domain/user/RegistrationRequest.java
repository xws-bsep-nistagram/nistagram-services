package rs.ac.uns.ftn.nistagram.user.domain.user;

import lombok.Getter;

@Getter
public class RegistrationRequest {

    private String username;
    private String password;
    private String email;

}
