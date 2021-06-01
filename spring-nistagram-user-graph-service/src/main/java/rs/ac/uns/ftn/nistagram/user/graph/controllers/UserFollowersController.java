package rs.ac.uns.ftn.nistagram.user.graph.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.user.graph.payload.UserPayload;
import rs.ac.uns.ftn.nistagram.user.graph.payload.UserRelationshipRequest;
import rs.ac.uns.ftn.nistagram.user.graph.services.UserFollowersService;
import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user-graph/")
public class UserFollowersController {

    private final UserFollowersService userFollowerService;
    private final ModelMapper modelMapper;

    public UserFollowersController(UserFollowersService userFollowerService) {
        this.userFollowerService = userFollowerService;
        this.modelMapper = new ModelMapper();
    }

    @GetMapping("/{username}/followers")
    public ResponseEntity<?> findFollowers(@PathVariable String username) {
        var users = userFollowerService.findFollowers(username)
                .stream()
                .map(e-> modelMapper.map(e, UserPayload.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{username}/following")
    public ResponseEntity<?> findFollowing(@PathVariable String username){
        var users = userFollowerService.findFollowing(username)
                .stream()
                .map(e-> modelMapper.map(e, UserPayload.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{username}/followers/pending")
    public ResponseEntity<?> findPendingFollowers(@PathVariable String username) {
        var users = userFollowerService.findPendingFollowers(username)
                .stream()
                .map(e-> modelMapper.map(e, UserPayload.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @PostMapping("/followers/accept")
    public ResponseEntity<?> acceptFollowRequest(@RequestBody @Valid UserRelationshipRequest userRelationshipRequest) {
        userFollowerService.acceptFollowRequest(userRelationshipRequest.getSubject(), userRelationshipRequest.getTarget());
        return ResponseEntity.ok("Request successfully processed");
    }

    @DeleteMapping("/followers/pending")
    public ResponseEntity<?> revokeFollowRequest(@RequestBody @Valid UserRelationshipRequest userRelationshipRequest) {
        userFollowerService.revokeFollowRequest(userRelationshipRequest.getSubject(), userRelationshipRequest.getTarget());
        return ResponseEntity.ok("Request successfully processed");
    }

    @DeleteMapping("/followers/pending/decline")
    public ResponseEntity<?> declineFollowRequest(@RequestBody @Valid UserRelationshipRequest userRelationshipRequest) {
        userFollowerService.declineFollowRequest(userRelationshipRequest.getSubject(), userRelationshipRequest.getTarget());
        return ResponseEntity.ok("Request successfully processed");
    }

    @PostMapping("/followers")
    public ResponseEntity<?> follow(@RequestBody @Valid UserRelationshipRequest userRelationshipRequest){
        userFollowerService.follow(userRelationshipRequest.getSubject(), userRelationshipRequest.getTarget());
        return ResponseEntity.ok("Request successfully processed");
    }

    @DeleteMapping("/followers")
    public ResponseEntity<?> unfollow(@RequestBody @Valid UserRelationshipRequest userRelationshipRequest){
        userFollowerService.unfollow(userRelationshipRequest.getSubject(), userRelationshipRequest.getTarget());
        return ResponseEntity.ok("Request successfully processed");
    }

}
