package rs.ac.uns.ftn.nistagram.user.graph.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.user.graph.controllers.payload.*;
import rs.ac.uns.ftn.nistagram.user.graph.domain.Recommendation;
import rs.ac.uns.ftn.nistagram.user.graph.domain.UserStats;
import rs.ac.uns.ftn.nistagram.user.graph.services.FollowerService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user-graph/")
public class FollowerController {

    private final FollowerService userFollowerService;
    private final ModelMapper modelMapper;

    public FollowerController(FollowerService userFollowerService) {
        this.userFollowerService = userFollowerService;
        this.modelMapper = new ModelMapper();
        this.modelMapper.typeMap(Recommendation.class, RecommendationResponse.class)
                .addMapping(Recommendation::getConnectionsUsername,
                        RecommendationResponse::setMutualConnectionsUsername);
    }

    @GetMapping("/followers")
    public ResponseEntity<?> findFollowers(@RequestHeader("username") String username) {
        var users = userFollowerService.findFollowers(username)
                .stream()
                .map(e -> modelMapper.map(e, UserPayload.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/following")
    public ResponseEntity<?> findFollowing(@RequestHeader("username") String username) {
        var users = userFollowerService.findFollowing(username)
                .stream()
                .map(e -> modelMapper.map(e, UserPayload.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("{username}/stats")
    public ResponseEntity<FollowerStatsResponse> getFollowerStats(@PathVariable String username) {
        UserStats stats = userFollowerService.getStats(username);
        return ResponseEntity.ok(modelMapper.map(stats, FollowerStatsResponse.class));
    }

    @GetMapping("followers/pending")
    public ResponseEntity<?> findPendingFollowers(@RequestHeader String username) {
        var users = userFollowerService.findPendingFollowers(username)
                .stream()
                .map(e -> modelMapper.map(e, UserPayload.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("follows/{target}")
    public ResponseEntity<?> checkFollowing(@RequestHeader("username") String subject, @PathVariable String target) {
        var response = new UserRelationshipResponse(userFollowerService.checkFollowing(subject, target));
        return ResponseEntity.ok(response);
    }

    @GetMapping("pending/{target}")
    public ResponseEntity<?> checkPending(@RequestHeader("username") String subject, @PathVariable String target) {
        var response = new UserRelationshipResponse(userFollowerService.checkPending(subject, target));
        return ResponseEntity.ok(response);
    }


    @PostMapping("/followers/accept/{subject}")
    public ResponseEntity<?> acceptFollowRequest(@RequestHeader("username") String target, @PathVariable String subject) {
        userFollowerService.acceptFollowRequest(subject, target);
        return ResponseEntity.ok("Request successfully processed");
    }

    @DeleteMapping("/followers/pending")
    public ResponseEntity<?> revokeFollowRequest(@RequestBody @Valid UserRelationshipRequest userRelationshipRequest) {
        userFollowerService.revokeFollowRequest(userRelationshipRequest.getSubject(), userRelationshipRequest.getTarget());
        return ResponseEntity.ok("Request successfully processed");
    }

    @DeleteMapping("/followers/decline/{target}")
    public ResponseEntity<?> declineFollowRequest(@RequestHeader("username") String subject, @PathVariable String target) {
        userFollowerService.declineFollowRequest(subject, target);
        return ResponseEntity.ok("Request successfully processed");
    }

    @GetMapping("/followers/{target}")
    public ResponseEntity<?> follow(@RequestHeader("username") String subject, @PathVariable String target) {
        userFollowerService.follow(subject, target);
        return ResponseEntity.ok("Request successfully processed");
    }

    @DeleteMapping("/followers/{target}")
    public ResponseEntity<?> unfollow(@RequestHeader("username") String subject, @PathVariable String target) {
        userFollowerService.unfollow(subject, target);
        return ResponseEntity.ok("Request successfully processed");
    }

    @GetMapping("recommend")
    public ResponseEntity<?> recommend(@RequestHeader("username") String username) {
        List<RecommendationResponse> recommendations =
                userFollowerService.recommend(username).stream()
                        .map(e -> modelMapper.map(e, RecommendationResponse.class))
                        .collect(Collectors.toList());
        return ResponseEntity.ok(recommendations);
    }

}
