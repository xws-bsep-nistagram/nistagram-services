package rs.ac.uns.ftn.nistagram.auth.http.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "http://user-service:9003/api/users")
public interface UserClient {

    @GetMapping("/banned/{username}")
    ProfileBannedResponse isBanned(@PathVariable String username);

}
