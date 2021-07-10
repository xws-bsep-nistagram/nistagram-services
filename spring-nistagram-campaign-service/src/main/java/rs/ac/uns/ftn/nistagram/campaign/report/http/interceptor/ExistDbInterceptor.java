package rs.ac.uns.ftn.nistagram.campaign.report.http.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpHeaders;

public class ExistDbInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(HttpHeaders.AUTHORIZATION, "Basic YWRtaW46");
        requestTemplate.header(HttpHeaders.CONTENT_TYPE, "application/xml");
    }
}
