package rs.ac.uns.ftn.nistagram.user.graph.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.user.graph.controllers.payload.CloseFriendRelationshipResponse;
import rs.ac.uns.ftn.nistagram.user.graph.controllers.payload.UserPayload;
import rs.ac.uns.ftn.nistagram.user.graph.services.CloseFriendService;

import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user-graph/close-friends")
public class CloseFriendController {

    private final CloseFriendService closeFriendService;
    private final ModelMapper mapper;

    public CloseFriendController(CloseFriendService closeFriendService) {
        this.closeFriendService = closeFriendService;
        this.mapper = new ModelMapper();
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestHeader("username") String username) {
        var closeFriends = closeFriendService.findCloseFriends(username)
                .stream()
                .map(e -> mapper.map(e, UserPayload.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(closeFriends);
    }

    @PostMapping("{target}")
    public ResponseEntity<?> create(@RequestHeader("username") String subject, @PathVariable String target) {
        closeFriendService.addCloseFriend(subject, target);
        return ResponseEntity.ok("Request successfully processed");
    }

    @GetMapping("{target}")
    public ResponseEntity<?> check(@RequestHeader("username") String subject, @PathVariable String target) {
        var response = new CloseFriendRelationshipResponse(closeFriendService.checkIfCloseFriends(subject, target));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{target}")
    public ResponseEntity<?> remove(@RequestHeader("username") String subject, @PathVariable String target) {
        closeFriendService.removeCloseFriend(subject, target);
        return ResponseEntity.ok("Request successfully processed");
    }

}
