package rs.ac.uns.ftn.nistagram.chat.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.chat.domain.ChatSession;
import rs.ac.uns.ftn.nistagram.chat.domain.Message;
import rs.ac.uns.ftn.nistagram.chat.exceptions.EntityNotFoundException;
import rs.ac.uns.ftn.nistagram.chat.exceptions.OperationNotPermittedException;
import rs.ac.uns.ftn.nistagram.chat.http.GraphClient;
import rs.ac.uns.ftn.nistagram.chat.repositories.ChatSessionRepository;
import rs.ac.uns.ftn.nistagram.chat.repositories.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ChatSessionService {

    private final ChatSessionRepository chatSessionRepository;
    private final MessageRepository messageRepository;
    private final GraphClient graphClient;
    private final ChatPublisher chatPublisher;


    @Transactional
    public Message pushToSession(Message message) {
        log.info("Saving a message {}", message.toString());

        message.setTime(LocalDateTime.now());
        message = messageRepository.save(message);

        ChatSession session = chatSessionRepository
                .findByParticipants(message.getSender(), message.getReceiver());

        if (session == null)
            session = buildSession(message);

        session.pushMessage(message);

        session = chatSessionRepository.save(session);

        chatPublisher.publishMessage(session.getId(), message);

        log.info("Message successfully saved to a session with an id: {}", session.getId());

        return message;
    }

    private ChatSession buildSession(Message message) {
        ChatSession session = new ChatSession(message.getSender(), message.getReceiver());

        if (graphClient.checkFollowing(message.getReceiver(), message.getSender()).isFollowing())
            session.setSessionStatus(ChatSession.SessionStatus.ACCEPTED);
        else
            session.setSessionStatus(ChatSession.SessionStatus.PENDING);

        chatPublisher.publishNewSession(message.getReceiver(), session);

        return session;
    }

    @Transactional
    public ChatSession decline(String caller, Long sessionId) {
        log.info("Declining a session with an id: {}", sessionId);

        ChatSession session = getChatSession(caller, sessionId);

        session.decline();

        log.info("Session with an id: {} successfully declined", sessionId);

        return chatSessionRepository.save(session);
    }

    @Transactional
    public ChatSession accept(String caller, Long sessionId) {
        log.info("Accepting a session with an id: {}", sessionId);

        ChatSession session = getChatSession(caller, sessionId);

        session.accept();

        log.info("Session with an id: {} successfully accepted", sessionId);

        return chatSessionRepository.save(session);
    }

    @Transactional
    public ChatSession delete(String caller, Long sessionId) {
        log.info("Deleting a session with an id: {}", sessionId);

        ChatSession session = getChatSession(caller, sessionId);

        chatSessionRepository.delete(session);

        log.info("Session with an id: {} successfully deleted", sessionId);

        return session;
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

        ChatSession session = getChatSession(caller, sessionId);

        log.info("Found {} messages for a session with an id {}", session.getMessages().size(), sessionId);

        return session.getMessages();
    }

    private ChatSession getChatSession(String caller, Long sessionId) {
        ChatSession session = chatSessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                String.format("Session with an id %d doesn't exist", sessionId)
                        ));

        if (!session.hasParticipant(caller))
            throw new OperationNotPermittedException("You don't have an access to this chat session.");
        return session;
    }

}
