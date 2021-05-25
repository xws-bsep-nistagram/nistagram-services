package rs.ac.uns.ftn.nistagram.auth.config;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class PropertyConfiguration {

    private String jwtSecret;

}
