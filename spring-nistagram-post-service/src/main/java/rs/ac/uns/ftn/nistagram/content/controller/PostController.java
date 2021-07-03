package rs.ac.uns.ftn.nistagram.content.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.content.controller.dto.input.CommentCreationDTO;
import rs.ac.uns.ftn.nistagram.content.controller.dto.input.LocationSearchDTO;
import rs.ac.uns.ftn.nistagram.content.controller.dto.input.PostCreationDTO;
import rs.ac.uns.ftn.nistagram.content.controller.dto.output.PostCountDTO;
import rs.ac.uns.ftn.nistagram.content.controller.mapper.DomainDTOMapper;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;
import rs.ac.uns.ftn.nistagram.content.service.PostService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("api/content/post")
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final DomainDTOMapper mapper;


    @PostMapping
    public ResponseEntity<?> create(@RequestHeader("username") String username,
                                    @Valid @RequestBody PostCreationDTO dto) {
        dto.setAuthor(username);
        Post createdPost = postService.create(mapper.toDomain(dto));
        return ResponseEntity.ok(mapper.toDto(createdPost));
    }

    // TODO: authorize agent
    @PostMapping("agent")
    public ResponseEntity<?> request(@Valid @RequestBody PostCreationDTO dto) {
        Post createdPost = postService.createForInfluencer(mapper.toDomain(dto));
        return ResponseEntity.ok(mapper.toDto(createdPost));
    }


    @PutMapping("approve/{postId}")
    public ResponseEntity<?> approveAd(@RequestHeader("username") String username,
                                       @PathVariable Long postId) {
        postService.approveAdPost(username, postId);
        return ResponseEntity.ok("Ad successfully approved.");
    }

    @DeleteMapping("{postId}")
    public ResponseEntity<?> delete(@RequestHeader("username") String username,
                                    @PathVariable String postId) {
        postService.delete(username, Long.parseLong(postId));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("admin/{postId}")
    public ResponseEntity<?> deleteAsAdmin(@PathVariable String postId) {
        postService.delete(Long.parseLong(postId));
        return ResponseEntity.ok().build();
    }

    @GetMapping("user/{username}")
    public ResponseEntity<?> getByUsername(@RequestHeader("username") String caller,
                                           @PathVariable String username) {
        return ResponseEntity.ok(
                postService.getByUsername(caller, username)
                        .stream().map(mapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("user/{username}/count")
    public ResponseEntity<PostCountDTO> getPostCount(@PathVariable String username) {
        Long postCount = postService.getPostCount(username);
        return ResponseEntity.ok(mapper.toDto(postCount));
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
    public ResponseEntity<?> getById(@RequestHeader("username") String caller,
                                     @PathVariable String postId) {
        return ResponseEntity.ok(mapper.toDto(postService.getById(Long.parseLong(postId), caller)));
    }

    @GetMapping("admin/{postId}")
    public ResponseEntity<?> getByIdAsAdmin(@PathVariable String postId) {
        return ResponseEntity.ok(mapper.toDto(postService.getByIdAsAdmin(Long.parseLong(postId))));
    }

    @GetMapping("/public/{postId}")
    public ResponseEntity<?> getById(@PathVariable String postId) {
        return ResponseEntity.ok(mapper.toDto(postService.getById(Long.parseLong(postId))));
    }

    @GetMapping("/public/user/{username}")
    public ResponseEntity<?> getByUsername(@PathVariable String username) {
        List<Post> publicPosts = postService.getAllPublicByUsername(username);
        return ResponseEntity.ok(
                publicPosts.stream()
                        .map(mapper::toDto)
                        .collect(Collectors.toList()));
    }

    @PostMapping("search/location")
    public ResponseEntity<?> searchByLocation(@RequestHeader("username") String caller,
                                              @RequestBody LocationSearchDTO dto) {
        return ResponseEntity.ok(
                postService.searchByLocation(dto.getStreet(), caller)
                        .stream().map(mapper::toDto).collect(Collectors.toList())
        );
    }

    @GetMapping("tagged/{username}")
    public ResponseEntity<?> searchByTagged(@PathVariable String username,
                                            @RequestHeader("username") String caller) {
        return ResponseEntity.ok(
                postService.searchByTagged(username, caller)
                        .stream().map(mapper::toDto).collect(Collectors.toList())
        );
    }

    @GetMapping("interactions")
    public ResponseEntity<?> getLikedAndDislikedPostsForUser(@RequestHeader("username") String caller) {
        return ResponseEntity.ok(
                postService.getLikedAndDisliked(caller)
                        .stream().map(mapper::toDto).collect(Collectors.toList())
        );
    }

    @GetMapping("like/{postId}")
    public ResponseEntity<?> like(@RequestHeader("username") String caller,
                                  @PathVariable String postId) {
        postService.like(Long.parseLong(postId), caller); // TODO Extract username from HTTP
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("like/{postId}")
    public ResponseEntity<?> deleteLike(@RequestHeader("username") String caller,
                                        @PathVariable String postId) {
        postService.deleteLike(Long.parseLong(postId), caller);
        return ResponseEntity.ok().build();
    }

    @GetMapping("dislike/{postId}")
    public ResponseEntity<?> dislike(@RequestHeader("username") String caller,
                                     @PathVariable String postId) {
        postService.dislike(Long.parseLong(postId), caller);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("dislike/{postId}")
    public ResponseEntity<?> deleteDislike(@RequestHeader("username") String caller,
                                           @PathVariable String postId) {
        postService.deleteDislike(Long.parseLong(postId), caller);
        return ResponseEntity.ok().build();
    }

    @PostMapping("comment")
    public ResponseEntity<?> comment(@RequestHeader("username") String caller,
                                     @RequestBody CommentCreationDTO dto) {
        dto.setAuthor(caller);
        postService.comment(mapper.toDomain(dto), dto.getPostId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("save/{postId}")
    public ResponseEntity<?> save(@RequestHeader("username") String caller,
                                  @PathVariable String postId) {
        postService.save(caller, Long.parseLong(postId));
        return ResponseEntity.ok().build();
    }

    @GetMapping("unsave/{postId}")
    public ResponseEntity<?> unsave(@RequestHeader("username") String caller,
                                    @PathVariable String postId) {
        postService.unsave(caller, Long.parseLong(postId));
        return ResponseEntity.ok().build();
    }

    @GetMapping("saved")
    public ResponseEntity<?> getSaved(@RequestHeader("username") String caller) {
        return ResponseEntity.ok(
                postService.getSaved(caller)
                        .stream()
                        .map(savedPost -> mapper.toDto(savedPost.getPost()))
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("collection")
    public ResponseEntity<?> getCollections(@RequestHeader("username") String caller) {
        return ResponseEntity.ok(postService.getCollections(caller)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList()));
    }

    @PostMapping("collection/{collectionName}")
    public ResponseEntity<?> createCollection(@RequestHeader("username") String caller,
                                              @PathVariable String collectionName) {
        postService.createCollection(caller, collectionName);
        return ResponseEntity.ok().build();
    }

    @GetMapping("collection/{collectionName}/add/{postId}")
    public ResponseEntity<?> addPostToCollection(@RequestHeader("username") String caller,
                                                 @PathVariable String collectionName,
                                                 @PathVariable String postId) {
        postService.addPostToCollection(caller, collectionName, Long.parseLong(postId));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("collection/{collectionName}/remove/{postId}")
    public ResponseEntity<?> removePostFromCollection(@RequestHeader("username") String caller,
                                                      @PathVariable String collectionName,
                                                      @PathVariable String postId) {
        postService.removePostFromCollection(caller, collectionName, Long.parseLong(postId));
        return ResponseEntity.ok().build();
    }

    @GetMapping("collection/{collectionName}")
    public ResponseEntity<?> getCollectionPosts(@RequestHeader("username") String caller,
                                                @PathVariable String collectionName) {
        return ResponseEntity.ok(
                postService.getAllFromCollection(caller, collectionName)
                        .stream().map(postInCollection -> mapper.toDto(postInCollection.getPost()))
                        .collect(Collectors.toList())
        );
    }

    @DeleteMapping("collection/{collectionName}")
    public ResponseEntity<?> deleteCollection(@RequestHeader("username") String caller,
                                              @PathVariable String collectionName) {
        postService.deleteCollection(caller, collectionName);
        return ResponseEntity.ok().build();
    }
}
