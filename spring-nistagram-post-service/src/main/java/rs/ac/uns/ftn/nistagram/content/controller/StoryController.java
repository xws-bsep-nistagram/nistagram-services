package rs.ac.uns.ftn.nistagram.content.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.content.controller.dto.input.MediaStoryCreationDTO;
import rs.ac.uns.ftn.nistagram.content.controller.dto.input.ShareStoryCreationDTO;
import rs.ac.uns.ftn.nistagram.content.controller.dto.input.StoryCreationDTO;
import rs.ac.uns.ftn.nistagram.content.controller.mapper.DomainDTOMapper;
import rs.ac.uns.ftn.nistagram.content.service.StoryService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("api/content/story")
public class StoryController {

    private final StoryService storyService;
    private final DomainDTOMapper mapper;

    public StoryController(StoryService storyService, DomainDTOMapper mapper) {
        this.storyService = storyService;
        this.mapper = mapper;
    }

    @PostMapping("share")
    public ResponseEntity<?> createShareStory(@Valid @RequestBody ShareStoryCreationDTO dto) {
        String username = "nikola";
        dto.setAuthor(username);
        storyService.create(mapper.toDomain(dto, true));
        return ResponseEntity.ok().build();
    }

    @PostMapping("media")
    public ResponseEntity<?> createMediaStory(@Valid @RequestBody MediaStoryCreationDTO dto) {
        String username = "nikola";
        dto.setAuthor(username);
        storyService.create(mapper.toDomain(dto, false));
        return ResponseEntity.ok().build();
    }

    @GetMapping("user/{username}")
    public ResponseEntity<?> getByUsername(@PathVariable String username) {
        String caller = "nikola"; // TODO Extract username from HTTP
        return ResponseEntity.ok(
                storyService.getByUsername(username, caller)
                .stream().map(mapper::toDto).collect(Collectors.toList())
        );
    }

    @GetMapping("me")
    public ResponseEntity<?> getOwnActive() {
        String username = "nikola";
        return ResponseEntity.ok(
                storyService.getOwnActive(username)
                .stream().map(mapper::toDto).collect(Collectors.toList())
        );
    }

    @GetMapping("me/all")
    public ResponseEntity<?> getOwnAll() {
        String username = "nikola";
        return ResponseEntity.ok(
                storyService.getOwnAll(username)
                .stream().map(mapper::toDto).collect(Collectors.toList())
        );
    }
}
