package rs.ac.uns.ftn.nistagram.user.http.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "auth-service", url = "http://auth-service:9000/api/auth")
public interface AuthClient {

    @PostMapping("register")
    String register(Credentials credentials);

    @PutMapping("agent/{username}")
    String registerAgent(@PathVariable String username);

    @DeleteMapping("{username}")
    String delete(@PathVariable String username);

}
