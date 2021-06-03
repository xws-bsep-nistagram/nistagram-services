package rs.ac.uns.ftn.nistagram.user.http.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

// TODO: nekako namestiti da se ovo vuce iz env
@FeignClient(name = "auth-service", url = "http://localhost:9000/api/auth")
public interface AuthClient {

    @PostMapping("register")
    String register(Credentials credentials);

}
