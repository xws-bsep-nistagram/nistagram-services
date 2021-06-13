package rs.ac.uns.ftn.nistagram.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://gateway:9090")
                .allowedMethods("OPTIONS","GET","POST","PUT","DELETE","HEAD")
                .allowedHeaders("username");

        registry.addMapping("/api/auth/register")
                .allowedOrigins("http://user-service:9003")
                .allowedMethods("OPTIONS","GET","POST")
                .allowedHeaders("username");

    }
}
