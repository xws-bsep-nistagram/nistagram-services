package rs.ac.uns.ftn.nistagram.campaign.http.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import rs.ac.uns.ftn.nistagram.campaign.http.payload.Post;

@FeignClient(name = "post-service", url = "http://post-service:9002/api/content/post")
public interface PostClient {

    @PostMapping("agent/post")
    Post createAgentPost(@RequestHeader("username") String agentUsername, Post post);

    @DeleteMapping("/{id}")
    void deleteAgentPost(@RequestHeader("username") String agentUsername, @PathVariable Long id);

}
