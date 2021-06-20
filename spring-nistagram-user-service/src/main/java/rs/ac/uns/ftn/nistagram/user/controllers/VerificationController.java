package rs.ac.uns.ftn.nistagram.user.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.nistagram.user.controllers.dtos.VerificationRequestDTO;
import rs.ac.uns.ftn.nistagram.user.controllers.mappers.VerificationRequestMapper;
import rs.ac.uns.ftn.nistagram.user.service.VerificationService;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("api/verification")
public class VerificationController {

    private final VerificationService service;
    private final VerificationRequestMapper mapper;

    @PostMapping
    public String create(@RequestHeader("username") String username, @Valid @RequestBody VerificationRequestDTO request) {
        service.create(username, mapper.map(request));
        return "Verification request successfully registered.";
    }

}
