package rs.ac.uns.ftn.nistagram.api.gateway.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Getter
@Setter
public class ApiToken {
    private long id;
    private String packageName;
    private String agent;

    public static final String EXTERNAL_APP_ROLE = "AGENT_APP";

    public Authentication getAuthentication() {
        return new UsernamePasswordAuthenticationToken(
                this,
                null,
                List.of(new SimpleGrantedAuthority("ROLE_" + EXTERNAL_APP_ROLE))
        );
    }
}
