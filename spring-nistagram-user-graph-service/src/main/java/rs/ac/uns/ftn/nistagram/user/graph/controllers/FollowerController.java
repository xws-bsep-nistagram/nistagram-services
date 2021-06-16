package rs.ac.uns.ftn.nistagram.user.graph.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.user.graph.controllers.payload.FollowerStatsResponse;
import rs.ac.uns.ftn.nistagram.user.graph.controllers.payload.UserRelationshipResponse;
import rs.ac.uns.ftn.nistagram.user.graph.controllers.payload.UserPayload;
import rs.ac.uns.ftn.nistagram.user.graph.controllers.payload.UserRelationshipRequest;
import rs.ac.uns.ftn.nistagram.user.graph.domain.UserStats;
import rs.ac.uns.ftn.nistagram.user.graph.services.FollowerService;
import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user-graph/")
public class FollowerController {

    private final FollowerService userFollowerService;
    private final ModelMapper modelMapper;

    public FollowerController(FollowerService userFollowerService) {
        this.userFollowerService = userFollowerService;
        this.modelMapper = new ModelMapper();
    }

    @GetMapping("/followers")
    public ResponseEntity<?> findFollowers(@RequestHeader("username") String username) {
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

    @GetMapping("{username}/stats")
    public ResponseEntity<FollowerStatsResponse> getFollowerStats(@PathVariable String username) {
        UserStats stats = userFollowerService.getStats(username);
        return ResponseEntity.ok(modelMapper.map(stats, FollowerStatsResponse.class));
    }

    @GetMapping("/{username}/followers/pending")
    public ResponseEntity<?> findPendingFollowers(@PathVariable String username) {
        var users = userFollowerService.findPendingFollowers(username)
                .stream()
                .map(e-> modelMapper.map(e, UserPayload.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("follows/{target}")
    public ResponseEntity<?> checkFollowing(@RequestHeader("username") String subject, @PathVariable String target){
        var response = new UserRelationshipResponse(userFollowerService.checkFollowing(subject, target));
        return ResponseEntity.ok(response);
    }

    @GetMapping("pending/{target}")
    public ResponseEntity<?> checkPending(@RequestHeader("username") String subject, @PathVariable String target){
        var response = new UserRelationshipResponse(userFollowerService.checkPending(subject, target));
        return ResponseEntity.ok(response);
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

    @GetMapping("/followers/{target}")
    public ResponseEntity<?> follow(@RequestHeader("username") String subject, @PathVariable String target){
        userFollowerService.follow(subject, target);
        return ResponseEntity.ok("Request successfully processed");
    }

    @DeleteMapping("/followers/{target}")
    public ResponseEntity<?> unfollow(@RequestHeader("username") String subject, @PathVariable String target){
        userFollowerService.unfollow(subject, target);
        return ResponseEntity.ok("Request successfully processed");
    }

}
