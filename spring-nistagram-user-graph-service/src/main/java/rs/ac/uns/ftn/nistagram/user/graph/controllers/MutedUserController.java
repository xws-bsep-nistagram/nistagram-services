package rs.ac.uns.ftn.nistagram.user.graph.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.user.graph.controllers.payload.MutedRelationshipResponse;
import rs.ac.uns.ftn.nistagram.user.graph.controllers.payload.UserPayload;
import rs.ac.uns.ftn.nistagram.user.graph.services.MutedUserService;

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

    @GetMapping("/muted")
    public ResponseEntity<?> findMutedUsers(@RequestHeader("username") String subject) {
        var users = mutedUserService.findMutedUsers(subject)
                .stream()
                .map(e -> modelMapper.map(e, UserPayload.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/muted/{target}")
    public ResponseEntity<?> hasMuted(@RequestHeader("username") String subject, @PathVariable String target) {
        return ResponseEntity.ok(new MutedRelationshipResponse(mutedUserService.hasMuted(subject, target)));
    }

    @PostMapping("/mute/{target}")
    public ResponseEntity<?> mute(@RequestHeader("username") String subject, @PathVariable String target) {
        mutedUserService.mute(subject, target);
        return ResponseEntity.ok("Request successfully processed");
    }

    @DeleteMapping("/mute/{target}")
    public ResponseEntity<?> unmute(@RequestHeader("username") String subject, @PathVariable String target) {
        mutedUserService.unmute(subject, target);
        return ResponseEntity.ok("Request successfully processed");
    }

}
