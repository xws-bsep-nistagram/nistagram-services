package rs.ac.uns.ftn.nistagram.api.gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import rs.ac.uns.ftn.nistagram.api.gateway.domain.AuthToken;
import rs.ac.uns.ftn.nistagram.api.gateway.domain.AuthoritiesRequest;
import rs.ac.uns.ftn.nistagram.api.gateway.http.AuthClient;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Service
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final AuthClient authClient;

    public JwtTokenAuthenticationFilter(AuthClient authClient) {
        this.authClient = authClient;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwt = getJwtFromHeader(authHeader);

        if (jwt != null) {
            AuthToken authToken = requestAuthToken(jwt);

            log.info("auth-service returned user '{}' with roles: {}", authToken.getUsername(), authToken.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authToken.getAuthentication());
        }
        filterChain.doFilter(request, response);
    }

    private AuthToken requestAuthToken(String jwt) {
            return authClient.getAuthToken(new AuthoritiesRequest(jwt));
    }

    private String getJwtFromHeader(String authHeader) {
        if (authHeader == null || authHeader.isEmpty() || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        String[] headerTokens = authHeader.split(" ");
        return headerTokens.length == 2 ? headerTokens[1] : null;
    }

}
