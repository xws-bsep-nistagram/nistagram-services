package rs.ac.uns.ftn.nistagram.user.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.nistagram.user.controllers.dtos.RegistrationRequestDTO;
import rs.ac.uns.ftn.nistagram.user.controllers.mappers.RegistrationRequestMapper;
import rs.ac.uns.ftn.nistagram.user.service.RegistrationService;

import javax.validation.Valid;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final RegistrationService registrationService;
    private final RegistrationRequestMapper registrationRequestMapper;

    public UserController(RegistrationService registrationService, RegistrationRequestMapper registrationRequestMapper) {
        this.registrationService = registrationService;
        this.registrationRequestMapper = registrationRequestMapper;
    }

    @PostMapping("register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegistrationRequestDTO request) {
        String jwt = registrationService.register(registrationRequestMapper.toDomain(request));
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
    }

}
