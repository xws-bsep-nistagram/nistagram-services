package rs.ac.uns.ftn.nistagram.api.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HeaderConfig {

    @Value("${nistagram.headers.agent}")
    private String agentHeader;
    @Value("${nistagram.headers.user}")
    private String userHeader;
    @Value("${nistagram.headers.app}")
    private String appHeader;

    public String getAgentHeader() {
        return agentHeader;
    }

    public String getUserHeader() {
        return userHeader;
    }

    public String getAppHeader() {
        return appHeader;
    }
}
