package rs.ac.uns.ftn.nistagram.chat.controllers.mappers;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.chat.controllers.payload.responses.ChatSessionResponse;
import rs.ac.uns.ftn.nistagram.chat.domain.ChatSession;

@Component
public class ChatSessionMapper {

    public ChatSessionResponse toDto(ChatSession session, String caller) {
        return ChatSessionResponse
                .builder()
                .id(session.getId())
                .partner(session.getInitiatorUsername().equals(caller) ? session.getRecipientUsername()
                        : session.getInitiatorUsername())
                .sessionStatus(session.getSessionStatus())
                .build();

    }

}
