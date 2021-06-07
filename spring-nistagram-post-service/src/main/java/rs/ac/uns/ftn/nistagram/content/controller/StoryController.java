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
        String caller = "loremipsum1";
        dto.setAuthor(caller);
        storyService.create(mapper.toDomain(dto, true));
        return ResponseEntity.ok().build();
    }

    @PostMapping("media")
    public ResponseEntity<?> createMediaStory(@Valid @RequestBody MediaStoryCreationDTO dto) {
        String caller = "loremipsum1";
        dto.setAuthor(caller);
        storyService.create(mapper.toDomain(dto, false));
        return ResponseEntity.ok().build();
    }

    @GetMapping("user/{username}")
    public ResponseEntity<?> getByUsername(@PathVariable String username) {
        String caller = "loremipsum1"; // TODO Extract username from HTTP
        return ResponseEntity.ok(
                storyService.getByUsername(username, caller)
                .stream().map(mapper::toDto).collect(Collectors.toList())
        );
    }

    @GetMapping("user/{username}/restricted")
    @CrossOrigin("http://feed-service:9001")
    public ResponseEntity<?> getByUsernameRestricted(@PathVariable String username) {
        return ResponseEntity.ok(
                storyService.getByUsername(username)
                        .stream().map(mapper::toDto).collect(Collectors.toList())
        );
    }

    @GetMapping("me")
    public ResponseEntity<?> getOwnActive() {
        String caller = "loremipsum1";
        return ResponseEntity.ok(
                storyService.getOwnActive(caller)
                .stream().map(mapper::toDto).collect(Collectors.toList())
        );
    }

    @GetMapping("me/all")
    public ResponseEntity<?> getOwnAll() {
        String caller = "loremipsum1";
        return ResponseEntity.ok(
                storyService.getOwnAll(caller)
                .stream().map(mapper::toDto).collect(Collectors.toList())
        );
    }

    @DeleteMapping("{storyId}")
    public ResponseEntity<?> delete(@PathVariable String storyId) {
        String username = "loremipsum1"; // TODO Extract username from HTTP
        storyService.delete(Long.parseLong(storyId), username);
        return ResponseEntity.ok().build();
    }

    @PostMapping("highlights/{name}")
    public ResponseEntity<?> createStoryHighlights(@PathVariable String name) {
        String username = "loremipsum1"; // TODO Extract username from HTTP
        storyService.createStoryHighlights(name, username);
        return ResponseEntity.ok().build();
    }

    @PostMapping("highlights/{highlightId}/story/{storyId}")
    public ResponseEntity<?> addStoryToHighlight(@PathVariable String highlightId, @PathVariable String storyId) {
        String username = "loremipsum1"; // TODO Extract username from HTTP
        storyService.addStoryToHighlights(Long.parseLong(highlightId), Long.parseLong(storyId), username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("highlights/user/{username}")
    public ResponseEntity<?> getHighlightsByUsername(@PathVariable String username) {
        String caller = "loremipsum1"; // TODO Extract username from HTTP
        return ResponseEntity.ok(
                storyService.getHighlightsByUsername(username, caller)
                .stream().map(mapper::toDto).collect(Collectors.toList())
        );
    }

    @GetMapping("highlights/{highlightId}")
    public ResponseEntity<?> getStoriesFromHighlight(@PathVariable String highlightId) {
        String caller = "loremipsum1"; // TODO Extract username from HTTP
        return ResponseEntity.ok(
                storyService.getStoriesFromHighlight(Long.parseLong(highlightId), caller)
                .stream().map(mapper::toDto).collect(Collectors.toList())
        );
    }

    @DeleteMapping("highlights/{highlightId}")
    public ResponseEntity<?> deleteHighlight(@PathVariable String highlightId) {
        String caller = "loremipsum1"; // TODO Extract username from HTTP
        storyService.deleteHighlight(Long.parseLong(highlightId), caller);
        return ResponseEntity.ok().build();
    }
}
