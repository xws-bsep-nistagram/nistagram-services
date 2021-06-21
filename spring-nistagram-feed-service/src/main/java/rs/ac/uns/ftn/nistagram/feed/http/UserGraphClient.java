package rs.ac.uns.ftn.nistagram.feed.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import rs.ac.uns.ftn.nistagram.feed.http.payload.user.MutedRelationshipResponse;
import rs.ac.uns.ftn.nistagram.feed.http.payload.user.UserPayload;

import java.util.List;

@FeignClient(name = "user-graph-service", url = "http://user-graph-service:9004/api/user-graph")
public interface UserGraphClient {

    @GetMapping("/followers")
    List<UserPayload> getFollowers(@RequestHeader("username") String username);

    @GetMapping("/{username}/close-friends")
    List<UserPayload> getCloseFriends(@PathVariable("username") String username);

    @GetMapping("/muted/{target}")
    MutedRelationshipResponse hasMuted(@RequestHeader("username") String subject, @PathVariable String target);

}
