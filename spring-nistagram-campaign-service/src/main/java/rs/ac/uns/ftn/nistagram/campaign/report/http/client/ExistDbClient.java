package rs.ac.uns.ftn.nistagram.campaign.report.http.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import rs.ac.uns.ftn.nistagram.campaign.report.domain.StatisticsDisplayBundle;
import rs.ac.uns.ftn.nistagram.campaign.report.http.interceptor.ExistDbInterceptor;

@FeignClient(
        name = "xml-existdb-client-service",
        url = "${nistagram.exist.rest-url}",
        configuration = ExistDbInterceptor.class
)
public interface ExistDbClient {

    @GetMapping("{url}")
    StatisticsDisplayBundle get(@PathVariable("url") String url);

    @PutMapping("{databasePath}")
    void put(@PathVariable("databasePath") String databasePath, @RequestBody StatisticsDisplayBundle statistics);
}
