package rs.ac.uns.ftn.nistagram.user.graph.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.user.graph.controllers.payload.BlockedRelationshipResponse;
import rs.ac.uns.ftn.nistagram.user.graph.controllers.payload.UserPayload;
import rs.ac.uns.ftn.nistagram.user.graph.services.BlockedUserService;

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
                .map(e -> modelMapper.map(e, UserPayload.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/blocked/{target}")
    public ResponseEntity<?> hasBlocked(@RequestHeader("username") String subject, @PathVariable String target) {
        return ResponseEntity.ok(new BlockedRelationshipResponse(blockedUserService.hasBlocked(subject, target)));
    }

    @GetMapping("/blocked-by/{subject}")
    public ResponseEntity<?> isBlockedBy(@RequestHeader("username") String target, @PathVariable String subject) {
        return ResponseEntity.ok(new BlockedRelationshipResponse(blockedUserService.hasBlocked(subject, target)));
    }

    @PostMapping("/block/{target}")
    public ResponseEntity<?> block(@RequestHeader("username") String subject, @PathVariable String target) {
        blockedUserService.block(subject, target);
        return ResponseEntity.ok("Request successfully processed");
    }

    @DeleteMapping("/block/{target}")
    public ResponseEntity<?> unblock(@RequestHeader("username") String subject, @PathVariable String target) {
        blockedUserService.unblock(subject, target);
        return ResponseEntity.ok("Request successfully processed");
    }

}
