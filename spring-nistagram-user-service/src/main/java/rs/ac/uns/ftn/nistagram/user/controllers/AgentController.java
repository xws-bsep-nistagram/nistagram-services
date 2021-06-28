package rs.ac.uns.ftn.nistagram.user.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.user.controllers.dtos.agent.AgentRegistrationRequestDTO;
import rs.ac.uns.ftn.nistagram.user.controllers.dtos.agent.UserPromotionRequest;
import rs.ac.uns.ftn.nistagram.user.controllers.dtos.profile.ProfileViewDTO;
import rs.ac.uns.ftn.nistagram.user.controllers.mappers.ProfileMapper;
import rs.ac.uns.ftn.nistagram.user.service.AgentService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("api/users")
public class AgentController {

    private final AgentService service;
    private final ProfileMapper profileMapper;

    @PostMapping("agents")
    public String registerRequest(@RequestHeader("username") String username,
                                  @Valid @RequestBody AgentRegistrationRequestDTO request) {
        service.registerRequest(username, request.getWebsite());
        return "Request successfully registered.";
    }

    @GetMapping("agents/pending")
    public List<ProfileViewDTO> getAllPending() {
        return service.getAllPending()
                .stream()
                .map(profileMapper::map)
                .collect(Collectors.toList());
    }

    @GetMapping("non-promoted")
    public List<ProfileViewDTO> getNonPromoted() {
        return service.getNonPromoted()
                .stream()
                .map(profileMapper::map)
                .collect(Collectors.toList());
    }

    @PostMapping("agents/accept/{username}")
    public ProfileViewDTO accept(@PathVariable String username) {
        return profileMapper.map(service.accept(username));
    }

    @DeleteMapping("agents/decline/{username}")
    public ProfileViewDTO decline(@PathVariable String username) {
        return profileMapper.map(service.decline(username));
    }

    @PostMapping("agents/promote")
    public ProfileViewDTO promote(@RequestBody @Valid UserPromotionRequest request) {
        return profileMapper
                .map(service.promoteToAgent(request.getUsername(), request.getWebsite()));
    }

}
