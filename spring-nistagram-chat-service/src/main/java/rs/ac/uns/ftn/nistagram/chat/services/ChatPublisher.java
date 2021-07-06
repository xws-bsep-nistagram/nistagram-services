package rs.ac.uns.ftn.nistagram.chat.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.chat.controllers.mappers.ChatSessionMapper;
import rs.ac.uns.ftn.nistagram.chat.controllers.mappers.MessageMapper;
import rs.ac.uns.ftn.nistagram.chat.domain.ChatSession;
import rs.ac.uns.ftn.nistagram.chat.domain.Message;

@Slf4j
@Component
@AllArgsConstructor
public class ChatPublisher {

    private final SimpMessagingTemplate template;
    private final ChatSessionMapper chatSessionMapper;
    private final MessageMapper messageMapper;


    public void publishMessage(Long sessionId, Message message) {
        log.info("Publishing a message {} to a session with an id: {}", message, sessionId);
        this.template.convertAndSend(String.format("/topic/%d", sessionId), messageMapper.toDto(message));
    }

    public void publishNewSession(String username, ChatSession session) {
        log.info("Sending a new session {} to an user '{}'",
                session, username);
        this.template.convertAndSendToUser(username, "/queue/session",
                chatSessionMapper.toDto(session, username));
    }

}