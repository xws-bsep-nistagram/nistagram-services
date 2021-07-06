package rs.ac.uns.ftn.nistagram.chat.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-graph-service", url = "http://user-graph-service:9004/api/user-graph")
public interface GraphClient {

    @GetMapping("follows/{target}")
    UserRelationshipResponse checkFollowing(@RequestHeader("username") String subject,
                                            @PathVariable String target);

}
