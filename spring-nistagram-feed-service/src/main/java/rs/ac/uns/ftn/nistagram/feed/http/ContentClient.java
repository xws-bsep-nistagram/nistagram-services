package rs.ac.uns.ftn.nistagram.feed.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import rs.ac.uns.ftn.nistagram.feed.http.payload.post.ContentResponse;
import rs.ac.uns.ftn.nistagram.feed.http.payload.story.StoryResponse;

import java.util.List;

@FeignClient(name = "post-service", url = "http://post-service:9002/api/content")
public interface ContentClient {

    @GetMapping("story/user/{username}/restricted")
    List<StoryResponse> getStoriesByUsername(@PathVariable String username);

    @GetMapping("post/user/{username}/restricted")
    List<ContentResponse> getPostsByUsername(@PathVariable String username);

}
