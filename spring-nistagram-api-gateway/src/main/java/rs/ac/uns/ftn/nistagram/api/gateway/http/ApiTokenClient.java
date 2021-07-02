package rs.ac.uns.ftn.nistagram.api.gateway.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import rs.ac.uns.ftn.nistagram.api.gateway.domain.ApiToken;

@FeignClient(name = "api-token-service", url = "http://auth-service:9000/api/auth/api-token")
public interface ApiTokenClient {

    @GetMapping("decode/{apiTokenJWT}")
    ApiToken decodeApiToken(@PathVariable String apiTokenJWT);
}
