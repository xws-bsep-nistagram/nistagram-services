package rs.ac.uns.ftn.nistagram.chat.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.chat.controllers.mappers.ChatSessionMapper;
import rs.ac.uns.ftn.nistagram.chat.controllers.mappers.MessageMapper;
import rs.ac.uns.ftn.nistagram.chat.controllers.payload.requests.ContentShareMessageRequest;
import rs.ac.uns.ftn.nistagram.chat.controllers.payload.requests.TemporaryMediaMessageRequest;
import rs.ac.uns.ftn.nistagram.chat.controllers.payload.requests.TextMessageRequest;
import rs.ac.uns.ftn.nistagram.chat.controllers.payload.responses.ChatSessionResponse;
import rs.ac.uns.ftn.nistagram.chat.controllers.payload.responses.MessageResponse;
import rs.ac.uns.ftn.nistagram.chat.domain.Message;
import rs.ac.uns.ftn.nistagram.chat.services.ChatSessionService;
import rs.ac.uns.ftn.nistagram.chat.services.MessageService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("api/chat")
public class ChatController {

    private final ChatSessionService chatSessionService;
    private final MessageService messageService;
    private final MessageMapper messageMapper;
    private final ChatSessionMapper chatSessionMapper;

    @PostMapping("text-message")
    public ResponseEntity<MessageResponse> pushTextMessage(@RequestBody @Valid TextMessageRequest request,
                                                           @RequestHeader("username") String username) {
        request.setSender(username);
        Message pushedMessage = chatSessionService.pushToSession(messageMapper.toDomain(request));
        return ResponseEntity.ok(messageMapper.toDto(pushedMessage));
    }

    @PostMapping("temporary-message")
    public ResponseEntity<MessageResponse> pushTemporaryMessage(@RequestBody @Valid TemporaryMediaMessageRequest request,
                                                                @RequestHeader("username") String username) {

        request.setSender(username);
        Message pushedMessage = chatSessionService.pushToSession(messageMapper.toDomain(request));
        return ResponseEntity.ok(messageMapper.toDto(pushedMessage));
    }

    @PostMapping("share-message")
    public ResponseEntity<MessageResponse> pushShareMessage(@RequestBody @Valid ContentShareMessageRequest request,
                                                            @RequestHeader("username") String username) {
        request.setSender(username);
        Message pushedMessage = chatSessionService.pushToSession(messageMapper.toDomain(request));
        return ResponseEntity.ok(messageMapper.toDto(pushedMessage));
    }

    @GetMapping("session")
    public ResponseEntity<List<ChatSessionResponse>> getAllSessions(@RequestHeader("username") String username) {
        List<ChatSessionResponse> responses = chatSessionService
                .getAllByUsername(username)
                .stream()
                .map(chatSession -> chatSessionMapper.toDto(chatSession, username))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @GetMapping("session/{sessionId}/messages")
    public ResponseEntity<List<MessageResponse>> getAllBySessionId(@PathVariable("sessionId") Long sessionId,
                                                                   @RequestHeader("username") String username) {
        List<MessageResponse> responses = chatSessionService
                .getAllBySessionId(username, sessionId)
                .stream()
                .map(messageMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @PutMapping("message/{messageId}/seen")
    public ResponseEntity<MessageResponse> markAsSeen(@PathVariable("messageId") Long messageId,
                                                      @RequestHeader("username") String username) {
        return ResponseEntity.ok(messageMapper
                .toDto(messageService.markAsSeen(username, messageId)));
    }

    @PutMapping("temporary-message/{messageId}/opened")
    public ResponseEntity<MessageResponse> markAsOpened(@PathVariable("messageId") Long messageId,
                                                        @RequestHeader("username") String username) {
        return ResponseEntity.ok(messageMapper
                .toDto(messageService.markAsOpened(username, messageId)));
    }

}
