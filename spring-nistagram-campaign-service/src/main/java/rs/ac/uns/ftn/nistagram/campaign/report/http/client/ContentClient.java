package rs.ac.uns.ftn.nistagram.campaign.report.http.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import rs.ac.uns.ftn.nistagram.campaign.report.domain.Content;

@FeignClient(
        name = "content-client-service",
        url = "${nistagram.content.rest-url}"
)
public interface ContentClient {

    @GetMapping("post/admin/{postId}")
    Content getPostById(@PathVariable("postId") Long postId);

    @GetMapping("story/admin/{storyId}")
    Content getStoryById(@PathVariable("storyId") Long storyId);
}
