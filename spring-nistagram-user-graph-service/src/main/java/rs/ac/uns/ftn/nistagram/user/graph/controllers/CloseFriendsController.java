package rs.ac.uns.ftn.nistagram.user.graph.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.user.graph.controllers.payload.UserPayload;
import rs.ac.uns.ftn.nistagram.user.graph.controllers.payload.UserRelationshipRequest;
import rs.ac.uns.ftn.nistagram.user.graph.services.CloseFriendsService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user-graph/")
public class CloseFriendsController {

    private final CloseFriendsService closeFriendsService;
    private final ModelMapper mapper;

    public CloseFriendsController(CloseFriendsService closeFriendsService) {
        this.closeFriendsService = closeFriendsService;
        this.mapper = new ModelMapper();
    }

    @GetMapping("/{username}/close-friends")
    public ResponseEntity<?> findCloseFriends(@PathVariable String username){
        var closeFriends = closeFriendsService.findCloseFriends(username)
                                        .stream()
                                        .map(e-> mapper.map(e, UserPayload.class))
                                        .collect(Collectors.toList());
        return ResponseEntity.ok(closeFriends);
    }

    @PostMapping("close-friends")
    public ResponseEntity<?> addCloseFriends(@RequestBody @Valid UserRelationshipRequest relationshipRequest){
        closeFriendsService.addCloseFriend(relationshipRequest.getSubject(),
                relationshipRequest.getTarget());
        return ResponseEntity.ok("Request successfully processed");
    }

    @DeleteMapping("close-friends")
    public ResponseEntity<?> removeCloseFriends(@RequestBody @Valid UserRelationshipRequest relationshipRequest){
        closeFriendsService.removeCloseFriend(relationshipRequest.getSubject(),
                relationshipRequest.getTarget());
        return ResponseEntity.ok("Request successfully processed");
    }

}
