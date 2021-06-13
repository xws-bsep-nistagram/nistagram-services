package rs.ac.uns.ftn.nistagram.user.graph.config;

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

        registry.addMapping("/api/user-graph/**")
                .allowedOrigins("http://post-service:9002")
                .allowedMethods("GET")
                .allowedHeaders("username");

        registry.addMapping("/api/feed/**")
                .allowedOrigins("http://post-service:9001")
                .allowedMethods("GET")
                .allowedHeaders("username");

    }
}
