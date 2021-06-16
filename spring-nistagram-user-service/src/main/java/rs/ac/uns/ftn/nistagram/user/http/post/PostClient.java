package rs.ac.uns.ftn.nistagram.user.http.post;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "post-service", url = "http://post-service:9002/api/content/post")
public interface PostClient {

    @GetMapping("user/{username}/count")
    PostStats getPostStats(@PathVariable String username);

}
