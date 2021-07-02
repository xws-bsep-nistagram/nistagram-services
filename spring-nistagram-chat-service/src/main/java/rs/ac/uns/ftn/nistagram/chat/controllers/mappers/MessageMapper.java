package rs.ac.uns.ftn.nistagram.chat.controllers.mappers;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.chat.controllers.payload.requests.ContentShareMessageRequest;
import rs.ac.uns.ftn.nistagram.chat.controllers.payload.requests.TemporaryMediaMessageRequest;
import rs.ac.uns.ftn.nistagram.chat.controllers.payload.requests.TextMessageRequest;
import rs.ac.uns.ftn.nistagram.chat.controllers.payload.responses.ContentShareMessageResponse;
import rs.ac.uns.ftn.nistagram.chat.controllers.payload.responses.MessageResponse;
import rs.ac.uns.ftn.nistagram.chat.controllers.payload.responses.TemporaryMessageResponse;
import rs.ac.uns.ftn.nistagram.chat.controllers.payload.responses.TextMessageResponse;
import rs.ac.uns.ftn.nistagram.chat.domain.ContentShareMessage;
import rs.ac.uns.ftn.nistagram.chat.domain.Message;
import rs.ac.uns.ftn.nistagram.chat.domain.TemporaryMediaMessage;
import rs.ac.uns.ftn.nistagram.chat.domain.TextMessage;

@Component
public class MessageMapper {

    public TextMessage toDomain(TextMessageRequest request) {
        return TextMessage
                .builder()
                .sender(request.getSender())
                .receiver(request.getReceiver())
                .text(request.getText())
                .build();
    }

    public TemporaryMediaMessage toDomain(TemporaryMediaMessageRequest request) {
        return TemporaryMediaMessage
                .builder()
                .sender(request.getSender())
                .receiver(request.getReceiver())
                .mediaUrl(request.getMediaUrl())
                .build();
    }

    public ContentShareMessage toDomain(ContentShareMessageRequest request) {
        return ContentShareMessage
                .builder()
                .sender(request.getSender())
                .receiver(request.getReceiver())
                .contentId(request.getContentId())
                .build();
    }

    public MessageResponse toDto(Message message) {
        if (message instanceof TextMessage)
            return toTextMessageDto((TextMessage) message);
        else if (message instanceof TemporaryMediaMessage)
            return toTemporaryMessageDto((TemporaryMediaMessage) message);
        else if (message instanceof ContentShareMessage)
            return toContentShareMessageDto((ContentShareMessage) message);
        else
            return null;
    }


    private TextMessageResponse toTextMessageDto(TextMessage textMessage) {
        return TextMessageResponse
                .builder()
                .id(textMessage.getId())
                .sender(textMessage.getSender())
                .receiver(textMessage.getReceiver())
                .time(textMessage.getTime())
                .seen(textMessage.getSeen())
                .text(textMessage.getText())
                .build();
    }

    private TemporaryMessageResponse toTemporaryMessageDto(TemporaryMediaMessage message) {
        return TemporaryMessageResponse
                .builder()
                .id(message.getId())
                .sender(message.getSender())
                .receiver(message.getReceiver())
                .time(message.getTime())
                .seen(message.getSeen())
                .opened(message.isOpened())
                .mediaUrl(message.getMediaUrl())
                .build();
    }

    private ContentShareMessageResponse toContentShareMessageDto(ContentShareMessage message) {
        return ContentShareMessageResponse
                .builder()
                .id(message.getId())
                .sender(message.getSender())
                .receiver(message.getReceiver())
                .time(message.getTime())
                .seen(message.getSeen())
                .contentId(message.getContentId())
                .build();

    }

}
