package rs.ac.uns.ftn.nistagram.campaign.http.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import rs.ac.uns.ftn.nistagram.campaign.http.interceptor.ExistDbInterceptor;

@FeignClient(
        name = "xml-existdb-client-service",
        url = "${nistagram.exist.rest-url}",
        configuration = ExistDbInterceptor.class
)
public interface ExistDbClient {

    @GetMapping("{url}")
    String get(@PathVariable("url") String url);
}
