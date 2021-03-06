package rs.ac.uns.ftn.nistagram.api.gateway.filters;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import rs.ac.uns.ftn.nistagram.api.gateway.domain.ApiToken;
import rs.ac.uns.ftn.nistagram.api.gateway.http.ApiTokenClient;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ApiTokenAuthorizationFilter extends OncePerRequestFilter {

    private final ApiTokenClient apiTokenClient;

    private static final String API_TOKEN_HEADER = "apiToken";

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
            throws ServletException, IOException {

        final String apiTokenHeader = req.getHeader(API_TOKEN_HEADER);
        if (apiTokenHeader != null) {
            ApiToken apiToken = apiTokenClient.decodeApiToken(apiTokenHeader);

            log.info("auth-service returned external application {} from agent {}",
                    apiToken.getPackageName(), apiToken.getAgent());

            SecurityContextHolder.getContext().setAuthentication(apiToken.getAuthentication());
        }

        filterChain.doFilter(req, res);
    }
}
