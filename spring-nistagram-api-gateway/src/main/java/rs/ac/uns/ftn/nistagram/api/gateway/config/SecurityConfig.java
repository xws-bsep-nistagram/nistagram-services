package rs.ac.uns.ftn.nistagram.api.gateway.config;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import rs.ac.uns.ftn.nistagram.api.gateway.domain.ApiToken;
import rs.ac.uns.ftn.nistagram.api.gateway.filters.ApiTokenAuthorizationFilter;
import rs.ac.uns.ftn.nistagram.api.gateway.filters.GatewayRequestLoggingFilter;
import rs.ac.uns.ftn.nistagram.api.gateway.filters.JwtTokenAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private final JwtTokenAuthenticationFilter authFilter;
    private final ApiTokenAuthorizationFilter apiTokenFilter;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .headers().disable()
                .csrf().disable()
                .cors().and()
                .logout().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .anonymous()
                .and()
                .exceptionHandling().authenticationEntryPoint(
                (req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilterAfter(authFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(apiTokenFilter,
                        JwtTokenAuthenticationFilter.class)
                .addFilterBefore(new GatewayRequestLoggingFilter(), JwtTokenAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/api/auth/agent/**").hasRole("ADMIN")
                .antMatchers("/api/auth/api-token/test").hasRole(ApiToken.EXTERNAL_APP_ROLE)
                .antMatchers("/api/auth/api-token/**").hasRole("AGENT")
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/notification/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/users/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/users/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/users/taggable/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/users/public/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/content/post/public/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/content/story/public/**").permitAll()
                .antMatchers("/api/verification/admin/**").hasRole("ADMIN")
                .antMatchers("/api/**").hasRole("USER")
                .anyRequest().permitAll();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOrigins("http://localhost:8080")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("Accept", "Content-Type", "Origin",
                        "Authorization", "X-Auth-Token")
                .exposedHeaders("X-Auth-Token", "Authorization")
                .allowedMethods("POST", "GET", "DELETE", "PUT", "OPTIONS");
    }

}
