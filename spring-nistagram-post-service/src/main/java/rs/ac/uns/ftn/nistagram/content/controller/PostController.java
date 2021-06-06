package rs.ac.uns.ftn.nistagram.content.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.content.controller.dto.input.CommentCreationDTO;
import rs.ac.uns.ftn.nistagram.content.controller.dto.input.PostCreationDTO;
import rs.ac.uns.ftn.nistagram.content.controller.mapper.DomainDTOMapper;
import rs.ac.uns.ftn.nistagram.content.services.PostService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("api/content/post")
public class PostController {

    private final PostService postService;
    private final DomainDTOMapper mapper;

    public PostController(PostService postService, DomainDTOMapper mapper) {
        this.postService = postService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody PostCreationDTO dto) {
        postService.create(mapper.toDomain(dto));   // TODO Extract username from HTTP
        return ResponseEntity.ok().build();
    }

    @GetMapping("{postId}")
    public ResponseEntity<?> getById(@PathVariable String postId) {
        return ResponseEntity.ok(mapper.toDto(postService.getById(Long.parseLong(postId))));
    }

    @GetMapping("like/{postId}")
    public ResponseEntity<?> like(@PathVariable String postId) {
        postService.like(Long.parseLong(postId), "nikola"); // TODO Extract username from HTTP
        return ResponseEntity.ok().build();
    }

    @GetMapping("dislike/{postId}")
    public ResponseEntity<?> dislike(@PathVariable String postId) {
        postService.dislike(Long.parseLong(postId), "nikola"); // TODO Extract username from HTTP
        return ResponseEntity.ok().build();
    }

    @PostMapping("comment")
    public ResponseEntity<?> comment(@RequestBody CommentCreationDTO dto) {
        dto.setAuthor("nikola");    // TODO Extract username from HTTP
        postService.comment(mapper.toDomain(dto), dto.getPostId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("save/{postId}")
    public ResponseEntity<?> save(@PathVariable String postId) {
        String username = "nikola"; // TODO Extract username from HTTP
        postService.save(username, Long.parseLong(postId));
        return ResponseEntity.ok().build();
    }

    @GetMapping("unsave/{postId}")
    public ResponseEntity<?> unsave(@PathVariable String postId) {
        String username = "nikola"; // TODO Extract username from HTTP
        postService.unsave(username, Long.parseLong(postId));
        return ResponseEntity.ok().build();
    }

    @GetMapping("saved")
    public ResponseEntity<?> getSaved() {
        String username = "nikola"; // TODO Extract username from HTTP
        return ResponseEntity.ok(
                postService.getSaved(username)
                .stream().map(savedPost -> mapper.toDto(savedPost.getPost()))
                .collect(Collectors.toList())
        );
    }

    @PostMapping("collection/{collectionName}")
    public ResponseEntity<?> createCollection(@PathVariable String collectionName) {
        String username = "nikola"; // TODO Extract username from HTTP
        postService.createCollection(username, collectionName);
        return ResponseEntity.ok().build();
    }

    @GetMapping("collection/{collectionName}/add/{postId}")
    public ResponseEntity<?> addPostToCollection(@PathVariable String collectionName, @PathVariable String postId) {
        String username = "nikola"; // TODO Extract username from HTTP
        postService.addPostToCollection(username, collectionName, Long.parseLong(postId));
        return ResponseEntity.ok().build();
    }

    @GetMapping("collection/{collectionName}")
    public ResponseEntity<?> getCollectionPosts(@PathVariable String collectionName) {
        String username = "nikola"; // TODO Extract username from HTTP
        return ResponseEntity.ok(
                postService.getAllFromCollection(username, collectionName)
                        .stream().map(postInCollection -> mapper.toDto(postInCollection.getPost()))
                        .collect(Collectors.toList())
        );
    }

    @DeleteMapping("collection/{collectionName}")
    public ResponseEntity<?> deleteCollection(@PathVariable String collectionName) {
        String username = "nikola"; // TODO Extract username from HTTP
        postService.deleteCollection(username, collectionName);
        return ResponseEntity.ok().build();
    }
}
