package rs.ac.uns.ftn.nistagram.user.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.nistagram.user.controllers.dtos.agent.AgentRegistrationRequestDTO;
import rs.ac.uns.ftn.nistagram.user.service.AgentService;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("api/users/agents")
public class AgentController {

    private final AgentService service;

    @PostMapping
    public String registerRequest(@RequestHeader("username") String username,
                                  @Valid @RequestBody AgentRegistrationRequestDTO request) {
        service.registerRequest(username, request.getWebsite());
        return "Request successfully registered.";
    }

}
