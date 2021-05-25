package rs.ac.uns.ftn.nistagram.auth.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import rs.ac.uns.ftn.nistagram.auth.controllers.dtos.AuthRequestDTO;
import rs.ac.uns.ftn.nistagram.auth.controllers.mappers.AuthRequestMapper;
import rs.ac.uns.ftn.nistagram.auth.service.AuthService;

@Controller
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService service;
    private final AuthRequestMapper mapper;

    public AuthController(AuthService service, AuthRequestMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<Void> authenticate(@RequestBody AuthRequestDTO authRequest) {
        String generatedToken = service.authenticate(mapper.toDomain(authRequest));
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, generatedToken).build();
    }

}
