package rs.ac.uns.ftn.nistagram.api.gateway.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.api.gateway.domain.ApiToken;

@Component
@Slf4j
public class AuthenticationZuulFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal.getClass() == String.class)
            ctx.addZuulRequestHeader("username", (String) principal);
        else if (principal.getClass() == ApiToken.class) {
            ApiToken apiToken = (ApiToken) principal;
            log.info("ZUUL Registered an external application request from: {}", apiToken.getPackageName());
            ctx.addZuulRequestHeader("agent", apiToken.getAgent());
            ctx.addZuulRequestHeader("app", apiToken.getPackageName());
        }

        return null;
    }
}
