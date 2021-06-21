package rs.ac.uns.ftn.nistagram.user.http.graph;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-graph-service", url = "http://user-graph-service:9004/api/user-graph")
public interface UserGraphClient {

    @GetMapping("{username}/stats")
    FollowerStats getFollowerStats(@PathVariable String username);

    @GetMapping("/blocked/{target}")
    BlockedRelationshipResponse hasBlocked(@RequestHeader("username") String subject,
                                           @PathVariable String target);

    @GetMapping("/blocked-by/{subject}")
    BlockedRelationshipResponse isBlockedBy(@RequestHeader("username") String target,
                                            @PathVariable String subject);

}
