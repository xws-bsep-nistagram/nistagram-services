package rs.ac.uns.ftn.nistagram.user.http.graph;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-graph-service", url = "http://user-graph-service:9004/api/user-graph")
public interface UserGraphClient {

    @GetMapping("{username}/stats")
    FollowerStats getFollowerStats(@PathVariable String username);

}
