package rs.ac.uns.ftn.nistagram.campaign.http;

import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "auth-service", url = "http://user-service:9003/api/users")
public interface UserClient {

    @GetMapping("query")
    List<String> getUsers(@QueryMap UserQuery query);

}
