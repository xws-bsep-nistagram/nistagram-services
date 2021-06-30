package rs.ac.uns.ftn.nistagram.auth.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.auth.domain.ApiToken;
import rs.ac.uns.ftn.nistagram.auth.service.apitokens.ApiTokenService;

@Controller
@RequestMapping("api/auth/api-token")
@AllArgsConstructor
public class ApiTokenController {

    private final ApiTokenService apiTokenService;

    @GetMapping("create/{packageName}")
    public ResponseEntity<String> createAndEncode(
            @RequestHeader("username") String agent,
            @PathVariable String packageName) {
        return ResponseEntity.ok(apiTokenService.createAndEncode(packageName, agent));
    }

    @GetMapping("decode/{apiTokenJWT}")
    public ResponseEntity<ApiToken> decodeJWT(@PathVariable String apiTokenJWT) {
        return ResponseEntity.ok(apiTokenService.decode(apiTokenJWT));
    }
}
