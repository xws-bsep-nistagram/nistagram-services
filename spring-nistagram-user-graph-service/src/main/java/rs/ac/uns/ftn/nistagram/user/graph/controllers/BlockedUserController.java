package rs.ac.uns.ftn.nistagram.user.graph.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.user.graph.controllers.payload.UserPayload;
import rs.ac.uns.ftn.nistagram.user.graph.controllers.payload.UserRelationshipRequest;
import rs.ac.uns.ftn.nistagram.user.graph.services.BlockedUserService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user-graph/")
public class BlockedUserController {

    private final BlockedUserService blockedUserService;
    private final ModelMapper modelMapper;

    public BlockedUserController(BlockedUserService blockedUserService) {
        this.blockedUserService = blockedUserService;
        this.modelMapper = new ModelMapper();
    }

    @GetMapping("/{username}/blocked")
    public ResponseEntity<?> findBlockedUsers(@PathVariable String username) {
        var users = blockedUserService.findBlocked(username)
                .stream()
                .map(e-> modelMapper.map(e, UserPayload.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @PostMapping("/block")
    public ResponseEntity<?> block(@RequestBody @Valid UserRelationshipRequest userRelationshipRequest){
        blockedUserService.block(userRelationshipRequest.getSubject(), userRelationshipRequest.getTarget());
        return ResponseEntity.ok("Request successfully processed");
    }

    @DeleteMapping("/block")
    public ResponseEntity<?> unblock(@RequestBody @Valid UserRelationshipRequest userRelationshipRequest){
        blockedUserService.unblock(userRelationshipRequest.getSubject(), userRelationshipRequest.getTarget());
        return ResponseEntity.ok("Request successfully processed");
    }

}
