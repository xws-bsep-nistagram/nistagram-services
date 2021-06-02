package rs.ac.uns.ftn.nistagram.user.http.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "auth-service", url = "http://localhost:9009/api/auth")
public interface AuthClient {

    @PostMapping("register")
    public void register(Credentials credentials);

}
