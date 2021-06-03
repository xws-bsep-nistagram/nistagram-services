package rs.ac.uns.ftn.nistagram.auth.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.nistagram.auth.controllers.dtos.AuthRequestDTO;
import rs.ac.uns.ftn.nistagram.auth.controllers.dtos.RegistrationRequestDTO;
import rs.ac.uns.ftn.nistagram.auth.controllers.mappers.AuthRequestMapper;
import rs.ac.uns.ftn.nistagram.auth.controllers.mappers.RegistrationRequestMapper;
import rs.ac.uns.ftn.nistagram.auth.service.AuthService;

import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService service;
    private final AuthRequestMapper authMapper;
    private final RegistrationRequestMapper registrationMapper;

    public AuthController(AuthService service, AuthRequestMapper mapper, RegistrationRequestMapper registrationMapper) {
        this.service = service;
        this.authMapper = mapper;
        this.registrationMapper = registrationMapper;
    }

    @PostMapping
    public ResponseEntity<Void> authenticate(@RequestBody AuthRequestDTO authRequest) {
        String generatedToken = service.authenticate(authMapper.toDomain(authRequest));
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, generatedToken).build();
    }

    @PostMapping("register")
    public String register(@Valid @RequestBody RegistrationRequestDTO registrationRequest) {
        String generatedToken = service.register(registrationMapper.toDomain(registrationRequest));
        return generatedToken;
    }

}
