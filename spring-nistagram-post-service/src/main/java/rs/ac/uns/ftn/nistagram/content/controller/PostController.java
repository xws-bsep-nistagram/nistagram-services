package rs.ac.uns.ftn.nistagram.content.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.content.controller.dto.input.PostCreationDTO;
import rs.ac.uns.ftn.nistagram.content.controller.mapper.DomainDTOMapper;
import rs.ac.uns.ftn.nistagram.content.service.PostService;

import javax.validation.Valid;

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
}
