package rs.ac.uns.ftn.nistagram.user.graph.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.user.graph.controllers.payload.UserPayload;
import rs.ac.uns.ftn.nistagram.user.graph.controllers.payload.UserRelationshipRequest;
import rs.ac.uns.ftn.nistagram.user.graph.services.MutedUserService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user-graph/")
public class MutedUserController {

    private final MutedUserService mutedUserService;
    private final ModelMapper modelMapper;

    public MutedUserController(MutedUserService mutedUserService) {
        this.mutedUserService = mutedUserService;
        this.modelMapper = new ModelMapper();
    }

    @GetMapping("/{username}/muted")
    public ResponseEntity<?> findMutedUsers(@PathVariable String username) {
        var users = mutedUserService.findMutedUsers(username)
                .stream()
                .map(e-> modelMapper.map(e, UserPayload.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @PostMapping("/mute")
    public ResponseEntity<?> mute(@RequestBody @Valid UserRelationshipRequest userRelationshipRequest){
        mutedUserService.mute(userRelationshipRequest.getSubject(), userRelationshipRequest.getTarget());
        return ResponseEntity.ok("Request successfully processed");
    }

    @DeleteMapping("/unmute")
    public ResponseEntity<?> unmute(@RequestBody @Valid UserRelationshipRequest userRelationshipRequest){
        mutedUserService.unmute(userRelationshipRequest.getSubject(), userRelationshipRequest.getTarget());
        return ResponseEntity.ok("Request successfully processed");
    }

}
