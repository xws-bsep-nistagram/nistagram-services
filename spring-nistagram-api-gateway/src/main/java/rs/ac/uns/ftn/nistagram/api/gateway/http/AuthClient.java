package rs.ac.uns.ftn.nistagram.api.gateway.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import rs.ac.uns.ftn.nistagram.api.gateway.domain.AuthToken;
import rs.ac.uns.ftn.nistagram.api.gateway.domain.AuthoritiesRequest;

@FeignClient(name = "auth-service", url = "http://auth-service:9000/api/auth")
public interface AuthClient {

    @PostMapping("token")
    AuthToken getAuthToken(AuthoritiesRequest request);

}
