package rs.ac.uns.ftn.nistagram.auth.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class PropertyConfiguration {

    @Value("${auth.jwt.secret}")
    private String jwtSecret;
}
