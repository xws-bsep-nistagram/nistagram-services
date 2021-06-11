package rs.ac.uns.ftn.nistagram.auth.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.auth.controllers.dtos.AuthRequestDTO;
import rs.ac.uns.ftn.nistagram.auth.controllers.dtos.AuthTokenDTO;
import rs.ac.uns.ftn.nistagram.auth.controllers.dtos.RegistrationRequestDTO;
import rs.ac.uns.ftn.nistagram.auth.controllers.dtos.TokenRequestDTO;
import rs.ac.uns.ftn.nistagram.auth.controllers.mappers.AuthRequestMapper;
import rs.ac.uns.ftn.nistagram.auth.controllers.mappers.AuthTokenMapper;
import rs.ac.uns.ftn.nistagram.auth.controllers.mappers.RegistrationRequestMapper;
import rs.ac.uns.ftn.nistagram.auth.domain.AuthToken;
import rs.ac.uns.ftn.nistagram.auth.service.AuthService;

import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService service;
    private final AuthRequestMapper authMapper;
    private final RegistrationRequestMapper registrationMapper;
    private final AuthTokenMapper tokenMapper;

    public AuthController(AuthService service,
                          AuthRequestMapper mapper,
                          RegistrationRequestMapper registrationMapper,
                          AuthTokenMapper tokenMapper) {
        this.service = service;
        this.authMapper = mapper;
        this.registrationMapper = registrationMapper;
        this.tokenMapper = tokenMapper;
    }

    @PostMapping
    public ResponseEntity<Void> authenticate(@RequestBody AuthRequestDTO authRequest) {
        String generatedToken = service.authenticate(authMapper.toDomain(authRequest));
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, generatedToken).build();
    }

    @PostMapping("register")
    public String register(@Valid @RequestBody RegistrationRequestDTO registrationRequest) {
        return service.register(registrationMapper.toDomain(registrationRequest));
    }

    @PostMapping("token")
    public AuthTokenDTO getAuthToken(@RequestBody TokenRequestDTO tokenRequest) {
        AuthToken token = service.getAuthToken(tokenRequest.getJwt());
        return tokenMapper.toDTO(token);
    }

    @GetMapping("/activate/{uuid}")
    public ResponseEntity<?> activateUser(@PathVariable String uuid) {
        service.activate(uuid);
        return ResponseEntity.ok("Account successfully activated");
    }

}
