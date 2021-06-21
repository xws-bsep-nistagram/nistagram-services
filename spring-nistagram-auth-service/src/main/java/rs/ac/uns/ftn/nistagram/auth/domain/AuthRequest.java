package rs.ac.uns.ftn.nistagram.auth.domain;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@AllArgsConstructor
public class AuthRequest {

    private final String username;
    private final String password;

    public UsernamePasswordAuthenticationToken convert() {
        return new UsernamePasswordAuthenticationToken(username, password);
    }

    public String getUsername() {
        return username;
    }

}
