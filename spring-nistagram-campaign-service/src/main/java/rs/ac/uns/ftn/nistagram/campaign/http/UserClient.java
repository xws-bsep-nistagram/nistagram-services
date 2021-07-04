package rs.ac.uns.ftn.nistagram.campaign.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "user-service", url = "http://user-service:9003/api/users")
public interface UserClient {

    @GetMapping("query")
    List<String> getUsers(@RequestParam Map<String, String> queryMap);

}
