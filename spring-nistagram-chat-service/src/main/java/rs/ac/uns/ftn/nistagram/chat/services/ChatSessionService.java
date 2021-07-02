package rs.ac.uns.ftn.nistagram.chat.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.chat.domain.ChatSession;
import rs.ac.uns.ftn.nistagram.chat.domain.Message;
import rs.ac.uns.ftn.nistagram.chat.repositories.ChatSessionRepository;
import rs.ac.uns.ftn.nistagram.chat.repositories.MessageRepository;
import rs.ac.uns.ftn.nistagram.exceptions.EntityNotFoundException;
import rs.ac.uns.ftn.nistagram.exceptions.OperationNotPermittedException;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ChatSessionService {

    private final ChatSessionRepository chatSessionRepository;
    private final MessageRepository messageRepository;


    @Transactional
    public Message pushToSession(Message message) {

        log.info("Saving a message {}", message.toString());

        message.setTime(LocalDateTime.now());
        message = messageRepository.save(message);

        ChatSession session = chatSessionRepository
                .findByParticipants(message.getSender(), message.getReceiver());

        if (session == null)
            session = new ChatSession(message.getSender(), message.getReceiver());

        session.pushMessage(message);

        session = chatSessionRepository.save(session);

        log.info("Message successfully saved to a session with an id: {}", session.getId());

        return message;

    }

    @Transactional(readOnly = true)
    public List<ChatSession> getAllByUsername(String username) {
        log.info("Retrieving all chat sessions for an user '{}'", username);

        List<ChatSession> session = chatSessionRepository
                .findByUsername(username);

        log.info("Found {} chat session(s) for an user '{}'", session.size(), username);

        return session;

    }

    @Transactional(readOnly = true)
    public List<Message> getAllBySessionId(String caller, Long sessionId) {
        log.info("Retrieving all chat messages for a session with an id: {}", sessionId);

        ChatSession session = chatSessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                String.format("Session with an id %d doesn't exist", sessionId)
                        ));

        if (!session.hasParticipant(caller))
            throw new OperationNotPermittedException("You don't have an access to this chat session.");

        log.info("Found {} messages for a session with an id {}", session.getMessages().size(), sessionId);

        return session.getMessages();

    }

}
