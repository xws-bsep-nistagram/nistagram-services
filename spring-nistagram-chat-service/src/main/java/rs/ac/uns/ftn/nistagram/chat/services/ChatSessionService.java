package rs.ac.uns.ftn.nistagram.chat.services;

import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.chat.domain.ChatSession;
import rs.ac.uns.ftn.nistagram.chat.domain.ContentShareMessage;
import rs.ac.uns.ftn.nistagram.chat.domain.Message;
import rs.ac.uns.ftn.nistagram.chat.exceptions.EntityNotFoundException;
import rs.ac.uns.ftn.nistagram.chat.exceptions.OperationNotPermittedException;
import rs.ac.uns.ftn.nistagram.chat.http.ContentClient;
import rs.ac.uns.ftn.nistagram.chat.http.GraphClient;
import rs.ac.uns.ftn.nistagram.chat.messaging.event.notifications.MessageRequestEvent;
import rs.ac.uns.ftn.nistagram.chat.messaging.event.notifications.NewMessageEvent;
import rs.ac.uns.ftn.nistagram.chat.messaging.event.payload.notification.NotificationType;
import rs.ac.uns.ftn.nistagram.chat.messaging.mappers.EventPayloadMapper;
import rs.ac.uns.ftn.nistagram.chat.repositories.ChatSessionRepository;
import rs.ac.uns.ftn.nistagram.chat.repositories.MessageRepository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ChatSessionService {

    private final ChatSessionRepository chatSessionRepository;
    private final MessageRepository messageRepository;
    private final GraphClient graphClient;
    private final ContentClient contentClient;
    private final ChatPublisher chatPublisher;
    private final EntityManager entityManager;
    private final ApplicationEventPublisher queuePublisher;
    private final EventPayloadMapper mapper;


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

        session = chatSessionRepository.saveAndFlush(session);

        entityManager.detach(message);

        if (message instanceof ContentShareMessage)
            checkShareRestrictions((ContentShareMessage) message);

        chatPublisher.publishMessage(session.getId(), message);
        publishNewMessage(message);

        log.info("Message successfully saved to a session with an id: {}", session.getId());

        return message;
    }

    private void publishNewMessage(Message message) {

        NewMessageEvent event = new NewMessageEvent(UUID.randomUUID().toString(),
                mapper.toPayload(message, NotificationType.NEW_MESSAGE));

        log.info("Publishing a new message event {}", event);

        queuePublisher.publishEvent(event);

    }

    private void publishMessageRequest(Message message) {

        MessageRequestEvent event = new MessageRequestEvent(UUID.randomUUID().toString(),
                mapper.toPayload(message, NotificationType.NEW_MESSAGE_REQUEST));

        log.info("Publishing a new message request event {}", event);

        queuePublisher.publishEvent(event);

    }

    private void checkShareRestrictions(ContentShareMessage message) {
        log.info("Checking share restrictions for a message: {}", message);
        if (message.isStoryReshare())
            checkStoryRestricted(message);
        else
            checkPostRestricted(message);
    }

    private void checkStoryRestricted(ContentShareMessage message) {
        log.info("Checking story share restrictions for a message with an id: {}", message.getId());
        try {
            contentClient.getStoryById(message.getReceiver(), message.getContentId());
        } catch (FeignException ex) {
            if (ex.status() == 403) {
                log.info("{} doesn't have an access to a shared story with an id: {}",
                        message.getReceiver(), message.getId());
                message.removeContentId();
            }
        }
    }

    private void checkPostRestricted(ContentShareMessage message) {
        try {
            contentClient.getPostById(message.getReceiver(), message.getContentId());
        } catch (FeignException ex) {
            if (ex.status() == 403) {
                log.info("{} doesn't have an access to a shared post with an id: {}",
                        message.getReceiver(), message.getId());
                message.removeContentId();
            }
        }
    }

    private ChatSession buildSession(Message message) {
        ChatSession session = new ChatSession(message.getSender(), message.getReceiver());

        if (graphClient.checkFollowing(message.getReceiver(), message.getSender()).isFollowing())
            session.setSessionStatus(ChatSession.SessionStatus.ACCEPTED);
        else {
            session.setSessionStatus(ChatSession.SessionStatus.PENDING);
            publishMessageRequest(message);
        }

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

        return filterRestricted(session.getMessages(), caller);
    }

    private List<Message> filterRestricted(List<Message> messages, String caller) {
        messages.forEach(message -> {
            if (message instanceof ContentShareMessage)
                checkShareRestrictions((ContentShareMessage) message, caller);
        });
        return messages;
    }

    private void checkShareRestrictions(ContentShareMessage message, String caller) {
        log.info("Checking share restrictions for a message: {}", message);
        if (message.isStoryReshare())
            checkStoryRestricted(message, caller);
        else
            checkPostRestricted(message, caller);
    }

    private void checkStoryRestricted(ContentShareMessage message, String caller) {
        log.info("Checking story share restrictions for a message with an id: {}", message.getId());
        try {
            contentClient.getStoryById(caller, message.getContentId());
        } catch (FeignException ex) {
            if (ex.status() == 403) {
                log.info("{} doesn't have an access to a shared story with an id: {}",
                        caller, message.getId());
                message.removeContentId();
            }
        }
    }

    private void checkPostRestricted(ContentShareMessage message, String caller) {
        try {
            contentClient.getPostById(caller, message.getContentId());
        } catch (FeignException ex) {
            if (ex.status() == 403) {
                log.info("{} doesn't have an access to a shared post with an id: {}",
                        caller, message.getId());
                message.removeContentId();
            }
        }
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
