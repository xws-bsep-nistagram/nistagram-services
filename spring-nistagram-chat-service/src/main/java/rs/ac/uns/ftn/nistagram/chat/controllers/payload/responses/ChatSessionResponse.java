package rs.ac.uns.ftn.nistagram.chat.controllers.payload.responses;

import lombok.*;
import rs.ac.uns.ftn.nistagram.chat.domain.ChatSession;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatSessionResponse {

    private Long id;
    private String initiator;
    private String recipient;
    private String partner;
    private ChatSession.SessionStatus sessionStatus;

}
