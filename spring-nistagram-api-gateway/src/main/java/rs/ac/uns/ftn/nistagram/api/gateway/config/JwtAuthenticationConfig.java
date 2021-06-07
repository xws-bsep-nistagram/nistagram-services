package rs.ac.uns.ftn.nistagram.api.gateway.config;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class JwtAuthenticationConfig {

    @Value("${nistagram.security.jwt.header:Authorization}")
    private String header;

    @Value("${nistagram.security.jwt.prefix:Bearer}")
    private String prefix;

    @Value("${nistagram.security.jwt.expiration:#{24*60*60}}")
    private int expiration; // default 24 hours

    @Value("${nistagram.security.jwt.secret}")
    private String secret;

}
