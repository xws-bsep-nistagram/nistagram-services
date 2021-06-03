package rs.ac.uns.ftn.nistagram.user.graph.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.user.graph.payload.UserPayload;
import rs.ac.uns.ftn.nistagram.user.graph.payload.UserRelationshipRequest;
import rs.ac.uns.ftn.nistagram.user.graph.services.UserMutesService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user-graph/")
public class UserMutesController {

    private final UserMutesService userMutesService;
    private final ModelMapper modelMapper;

    public UserMutesController(UserMutesService userMutesService) {
        this.userMutesService = userMutesService;
        this.modelMapper = new ModelMapper();
    }

    @GetMapping("/{username}/muted")
    public ResponseEntity<?> findMutedUsers(@PathVariable String username) {
        var users = userMutesService.findMutedUsers(username)
                .stream()
                .map(e-> modelMapper.map(e, UserPayload.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @PostMapping("/mute")
    public ResponseEntity<?> mute(@RequestBody @Valid UserRelationshipRequest userRelationshipRequest){
        userMutesService.mute(userRelationshipRequest.getSubject(), userRelationshipRequest.getTarget());
        return ResponseEntity.ok("Request successfully processed");
    }

    @DeleteMapping("/mute")
    public ResponseEntity<?> unmute(@RequestBody @Valid UserRelationshipRequest userRelationshipRequest){
        userMutesService.unmute(userRelationshipRequest.getSubject(), userRelationshipRequest.getTarget());
        return ResponseEntity.ok("Request successfully processed");
    }

}
