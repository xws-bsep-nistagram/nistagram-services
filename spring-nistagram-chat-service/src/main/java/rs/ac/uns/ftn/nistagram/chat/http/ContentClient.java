package rs.ac.uns.ftn.nistagram.chat.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "post-service", url = "http://post-service:9002/api/content")
public interface ContentClient {

    @GetMapping("post/{postId}")
    PostOverviewResponse getPostById(@RequestHeader("username") String caller,
                                     @PathVariable Long postId);

    @GetMapping("story/{storyId}")
    StoryOverviewResponse getStoryById(@RequestHeader("username") String caller,
                                       @PathVariable Long storyId);

}
