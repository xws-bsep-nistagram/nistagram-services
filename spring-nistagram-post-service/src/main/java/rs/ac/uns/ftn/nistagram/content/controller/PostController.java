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
        String caller = "loremipsum1";
        dto.setAuthor(caller);
        postService.create(mapper.toDomain(dto));   // TODO Extract username from HTTP
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{postId}")
    public ResponseEntity<?> delete(@PathVariable String postId) {
        String username = "loremipsum1"; // TODO Extract username from HTTP
        postService.delete(username, Long.parseLong(postId));
        return ResponseEntity.ok().build();
    }

    @GetMapping("user/{username}")
    public ResponseEntity<?> getByUsername(@PathVariable String username) {
        String caller = "loremipsum1";   // TODO Extract username from HTTP
        return ResponseEntity.ok(
                postService.getByUsername(caller, username)
                .stream().map(mapper::toDto)
                .collect(Collectors.toList())
        );
    }
    @GetMapping("user/{username}/restricted")
    @CrossOrigin("http://feed-service:9001")
    public ResponseEntity<?> getByUsernameRestricted(@PathVariable String username) {
        return ResponseEntity.ok(
                postService.getByUsername(username)
                        .stream().map(mapper::toDto)
                        .collect(Collectors.toList())
        );
    }


    @GetMapping("{postId}")
    public ResponseEntity<?> getById(@PathVariable String postId) {
        String caller = "loremipsum1";
        return ResponseEntity.ok(mapper.toDto(postService.getById(Long.parseLong(postId), caller)));
    }

    @GetMapping("like/{postId}")
    public ResponseEntity<?> like(@PathVariable String postId) {
        String caller = "loremipsum1";
        postService.like(Long.parseLong(postId), caller); // TODO Extract username from HTTP
        return ResponseEntity.ok().build();
    }

    @GetMapping("dislike/{postId}")
    public ResponseEntity<?> dislike(@PathVariable String postId) {
        String caller = "loremipsum1";
        postService.dislike(Long.parseLong(postId), caller); // TODO Extract username from HTTP
        return ResponseEntity.ok().build();
    }

    @PostMapping("comment")
    public ResponseEntity<?> comment(@RequestBody CommentCreationDTO dto) {
        dto.setAuthor("loremipsum1");    // TODO Extract username from HTTP
        postService.comment(mapper.toDomain(dto), dto.getPostId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("save/{postId}")
    public ResponseEntity<?> save(@PathVariable String postId) {
        String caller = "loremipsum1"; // TODO Extract username from HTTP
        postService.save(caller, Long.parseLong(postId));
        return ResponseEntity.ok().build();
    }

    @GetMapping("unsave/{postId}")
    public ResponseEntity<?> unsave(@PathVariable String postId) {
        String caller = "loremipsum1"; // TODO Extract username from HTTP
        postService.unsave(caller, Long.parseLong(postId));
        return ResponseEntity.ok().build();
    }

    @GetMapping("saved")
    public ResponseEntity<?> getSaved() {
        String caller = "loremipsum1"; // TODO Extract username from HTTP
        return ResponseEntity.ok(
                postService.getSaved(caller)
                .stream().map(savedPost -> mapper.toDto(savedPost.getPost()))
                .collect(Collectors.toList())
        );
    }

    @PostMapping("collection/{collectionName}")
    public ResponseEntity<?> createCollection(@PathVariable String collectionName) {
        String caller = "loremipsum1"; // TODO Extract username from HTTP
        postService.createCollection(caller, collectionName);
        return ResponseEntity.ok().build();
    }

    @GetMapping("collection/{collectionName}/add/{postId}")
    public ResponseEntity<?> addPostToCollection(@PathVariable String collectionName, @PathVariable String postId) {
        String caller = "loremipsum1"; // TODO Extract username from HTTP
        postService.addPostToCollection(caller, collectionName, Long.parseLong(postId));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("collection/{collectionName}/remove/{postId}")
    public ResponseEntity<?> removePostFromCollection(@PathVariable String collectionName, @PathVariable String postId) {
        String caller = "loremipsum1"; // TODO Extract username from HTTP
        postService.removePostFromCollection(caller, collectionName, Long.parseLong(postId));
        return ResponseEntity.ok().build();
    }

    @GetMapping("collection/{collectionName}")
    public ResponseEntity<?> getCollectionPosts(@PathVariable String collectionName) {
        String caller = "loremipsum1"; // TODO Extract username from HTTP
        return ResponseEntity.ok(
                postService.getAllFromCollection(caller, collectionName)
                        .stream().map(postInCollection -> mapper.toDto(postInCollection.getPost()))
                        .collect(Collectors.toList())
        );
    }

    @DeleteMapping("collection/{collectionName}")
    public ResponseEntity<?> deleteCollection(@PathVariable String collectionName) {
        String caller = "loremipsum1"; // TODO Extract username from HTTP
        postService.deleteCollection(caller, collectionName);
        return ResponseEntity.ok().build();
    }
}
