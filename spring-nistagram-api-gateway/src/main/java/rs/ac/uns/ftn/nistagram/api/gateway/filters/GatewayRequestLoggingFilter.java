package rs.ac.uns.ftn.nistagram.api.gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class GatewayRequestLoggingFilter extends AbstractRequestLoggingFilter {

    public GatewayRequestLoggingFilter() {
        super.setIncludeQueryString(true);
        super.setIncludeHeaders(true);
    }

    @Override
    protected void beforeRequest(HttpServletRequest httpServletRequest, String message) {
        log.info(message);
    }

    @Override
    protected void afterRequest(HttpServletRequest httpServletRequest, String message) {
        log.info(message);
    }
}
