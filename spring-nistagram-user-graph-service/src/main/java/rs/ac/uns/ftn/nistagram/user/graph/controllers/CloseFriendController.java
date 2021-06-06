package rs.ac.uns.ftn.nistagram.user.graph.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.user.graph.controllers.payload.UserFollowingResponse;
import rs.ac.uns.ftn.nistagram.user.graph.controllers.payload.UserPayload;
import rs.ac.uns.ftn.nistagram.user.graph.controllers.payload.UserRelationshipRequest;
import rs.ac.uns.ftn.nistagram.user.graph.services.CloseFriendService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user-graph/")
public class CloseFriendController {

    private final CloseFriendService closeFriendService;
    private final ModelMapper mapper;

    public CloseFriendController(CloseFriendService closeFriendService) {
        this.closeFriendService = closeFriendService;
        this.mapper = new ModelMapper();
    }

    @GetMapping("/{username}/close-friends")
    public ResponseEntity<?> findCloseFriends(@PathVariable String username){
        var closeFriends = closeFriendService.findCloseFriends(username)
                                        .stream()
                                        .map(e-> mapper.map(e, UserPayload.class))
                                        .collect(Collectors.toList());
        return ResponseEntity.ok(closeFriends);
    }

    @PostMapping("close-friends")
    public ResponseEntity<?> addCloseFriends(@RequestBody @Valid UserRelationshipRequest relationshipRequest){
        closeFriendService.addCloseFriend(relationshipRequest.getSubject(),
                relationshipRequest.getTarget());
        return ResponseEntity.ok("Request successfully processed");
    }

    @GetMapping("{subject}/close-friends/{target}")
    public ResponseEntity<?> checkCloseFriends(@PathVariable String subject, @PathVariable String target){
        var response = new UserFollowingResponse(closeFriendService.checkIfCloseFriends(subject, target));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("close-friends")
    public ResponseEntity<?> removeCloseFriends(@RequestBody @Valid UserRelationshipRequest relationshipRequest){
        closeFriendService.removeCloseFriend(relationshipRequest.getSubject(),
                relationshipRequest.getTarget());
        return ResponseEntity.ok("Request successfully processed");
    }

}
