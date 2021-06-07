package rs.ac.uns.ftn.nistagram.feed.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import rs.ac.uns.ftn.nistagram.feed.controllers.mappers.FeedPayloadMapper;
import rs.ac.uns.ftn.nistagram.feed.controllers.payload.FeedResponse;
import rs.ac.uns.ftn.nistagram.feed.services.FeedService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("api/feed")
public class FeedController {

    private final FeedService feedService;

    @GetMapping("posts/{username}")
    public ResponseEntity<?> getPostFeed(@PathVariable String username){
        List<FeedResponse> response = feedService
                .getPostFeedByUsername(username)
                .stream()
                .map(FeedPayloadMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("stories/{username}")
    public ResponseEntity<?> getStoryFeed(@PathVariable String username){
        List<FeedResponse> response = feedService
                .getStoryFeedByUsername(username)
                .stream()
                .map(FeedPayloadMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("stories/close/{username}")
    public ResponseEntity<?> getAllStoriesForCloseFriends(@PathVariable String username){
        List<FeedResponse> response = feedService
                .getCloseFriendStoryFeedByUsername(username)
                .stream()
                .map(FeedPayloadMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }


}
