package rs.ac.uns.ftn.nistagram.campaign.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import rs.ac.uns.ftn.nistagram.campaign.http.payload.Post;

@FeignClient(name = "post-service", url = "http://post-service:9002/api/content/post")
public interface PostClient {

    @PostMapping("agent/post")
    void createAgentPost(@RequestHeader("username") String agentUsername, Post dto);

}
