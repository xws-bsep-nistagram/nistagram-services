package rs.ac.uns.ftn.nistagram.user.http.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import rs.ac.uns.ftn.nistagram.user.domain.user.RegistrationRequest;

@AllArgsConstructor
@Getter
public class Credentials {

    private String username;
    private String password;
    private String email;

    public Credentials(RegistrationRequest request) {
        this.username = request.getUsername();
        this.password = request.getPassword();
        this.email = request.getEmail();
    }
}
