package rs.ac.uns.ftn.nistagram.chat.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.chat.domain.Message;
import rs.ac.uns.ftn.nistagram.chat.domain.TemporaryMediaMessage;
import rs.ac.uns.ftn.nistagram.chat.repositories.MessageRepository;
import rs.ac.uns.ftn.nistagram.exceptions.EntityNotFoundException;
import rs.ac.uns.ftn.nistagram.exceptions.OperationNotPermittedException;

@Slf4j
@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    @Transactional
    public Message markAsSeen(String caller, Long messageId) {

        Message foundMessage = messageRepository.findById(messageId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                String.format("Message with an id %d doesn't exist", messageId)
                        ));

        if (!foundMessage.hasReceiver(caller))
            throw new OperationNotPermittedException("You cannot mark the message you are not the recipient as seen");

        foundMessage.markAsSeen();

        foundMessage = messageRepository.save(foundMessage);

        return foundMessage;
    }

    @Transactional
    public TemporaryMediaMessage markAsOpened(String caller, Long messageId) {

        Message foundMessage = messageRepository.findById(messageId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                String.format("Message with an id %d doesn't exist", messageId)
                        ));

        if (!(foundMessage instanceof TemporaryMediaMessage))
            throw new OperationNotPermittedException("You cannot mark the message that is not " +
                    "an instance of temporary message as seen");

        if (!foundMessage.hasReceiver(caller))
            throw new OperationNotPermittedException("You cannot mark the message you are " +
                    "not the recipient as seen");

        TemporaryMediaMessage temporaryMediaMessage = (TemporaryMediaMessage) foundMessage;
        temporaryMediaMessage.markAsOpened();

        temporaryMediaMessage = messageRepository.save(temporaryMediaMessage);

        return temporaryMediaMessage;

    }
}
