package rs.ac.uns.ftn.nistagram.content.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.content.controller.dto.input.MediaStoryCreationDTO;
import rs.ac.uns.ftn.nistagram.content.controller.dto.input.ShareStoryCreationDTO;
import rs.ac.uns.ftn.nistagram.content.controller.mapper.DomainDTOMapper;
import rs.ac.uns.ftn.nistagram.content.domain.core.story.StoryHighlight;
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
    public ResponseEntity<?> createShareStory(@RequestHeader("username") String caller,
                                              @Valid @RequestBody ShareStoryCreationDTO dto) {
        dto.setAuthor(caller);
        storyService.create(mapper.toDomain(dto, true));
        return ResponseEntity.ok().build();
    }

    @PostMapping("media")
    public ResponseEntity<?> createMediaStory(@RequestHeader("username") String caller,
                                              @Valid @RequestBody MediaStoryCreationDTO dto) {
        dto.setAuthor(caller);
        storyService.create(mapper.toDomain(dto, false));
        return ResponseEntity.ok().build();
    }

    @GetMapping("user/{username}")
    public ResponseEntity<?> getByUsername(@RequestHeader("username") String caller,
                                           @PathVariable String username) {
        return ResponseEntity.ok(
                storyService.getByUsername(username, caller)
                        .stream().map(mapper::toDto).collect(Collectors.toList())
        );
    }

    @GetMapping("{storyId}")
    public ResponseEntity<?> getById(@RequestHeader("username") String caller,
                                     @PathVariable Long storyId) {
        return ResponseEntity.ok(
                mapper.toDto(storyService.getById(storyId, caller)));
    }

    @GetMapping("admin/{storyId}")
    public ResponseEntity<?> getByIdAsAdmin(@PathVariable Long storyId) {
        return ResponseEntity.ok(
                mapper.toDto(storyService.getByIdAsAdmin(storyId)));
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
    public ResponseEntity<?> getOwnActive(@RequestHeader("username") String caller) {
        return ResponseEntity.ok(
                storyService.getOwnActive(caller)
                        .stream().map(mapper::toDto).collect(Collectors.toList())
        );
    }

    @GetMapping("me/all")
    public ResponseEntity<?> getOwnAll(@RequestHeader("username") String caller) {
        return ResponseEntity.ok(
                storyService.getOwnAll(caller)
                        .stream().map(mapper::toDto).collect(Collectors.toList())
        );
    }

    @DeleteMapping("{storyId}")
    public ResponseEntity<?> delete(@RequestHeader("username") String caller,
                                    @PathVariable String storyId) {
        storyService.delete(Long.parseLong(storyId), caller);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("admin/{storyId}")
    public ResponseEntity<?> deleteAsAdmin(@PathVariable String storyId) {
        storyService.delete(Long.parseLong(storyId));
        return ResponseEntity.ok().build();
    }

    @PostMapping("highlights/{name}")
    public ResponseEntity<?> createStoryHighlights(@RequestHeader("username") String caller,
                                                   @PathVariable String name) {
        StoryHighlight created = storyService.createStoryHighlights(name, caller);
        return ResponseEntity.ok(mapper.toDto(created));
    }

    @PostMapping("highlights/{highlightId}/story/{storyId}")
    public ResponseEntity<?> addStoryToHighlight(@RequestHeader("username") String caller,
                                                 @PathVariable String highlightId,
                                                 @PathVariable String storyId) {
        storyService.addStoryToHighlights(Long.parseLong(highlightId), Long.parseLong(storyId), caller);
        return ResponseEntity.ok().build();
    }

    @GetMapping("highlights/user/{username}")
    public ResponseEntity<?> getHighlightsByUsername(@RequestHeader("username") String caller,
                                                     @PathVariable String username) {
        return ResponseEntity.ok(
                storyService.getHighlightsByUsername(username, caller)
                        .stream().map(mapper::toDto).collect(Collectors.toList())
        );
    }

    @GetMapping("public/highlights/user/{username}")
    public ResponseEntity<?> getHighlightsByUsernamePublic(@PathVariable String username) {
        return ResponseEntity.ok(
                storyService.getHighlightsByUsername(username)
                        .stream().map(mapper::toDto).collect(Collectors.toList())
        );
    }

    @GetMapping("highlights/{highlightId}")
    public ResponseEntity<?> getStoriesFromHighlight(@RequestHeader("username") String caller,
                                                     @PathVariable String highlightId) {
        return ResponseEntity.ok(
                storyService.getStoriesFromHighlight(Long.parseLong(highlightId), caller)
                        .stream().map(mapper::toDto).collect(Collectors.toList())
        );
    }

    @DeleteMapping("highlights/{highlightId}")
    public ResponseEntity<?> deleteHighlight(@RequestHeader("username") String caller,
                                             @PathVariable String highlightId) {
        storyService.deleteHighlight(Long.parseLong(highlightId), caller);
        return ResponseEntity.ok().build();
    }
}
