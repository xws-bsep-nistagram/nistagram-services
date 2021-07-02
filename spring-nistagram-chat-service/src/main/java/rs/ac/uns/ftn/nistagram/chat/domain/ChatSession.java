package rs.ac.uns.ftn.nistagram.chat.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatSession {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String initiatorUsername;
    private String recipientUsername;
    @OneToMany
    private List<Message> messages;

    public ChatSession(String initiatorUsername, String recipientUsername) {
        this.initiatorUsername = initiatorUsername;
        this.recipientUsername = recipientUsername;
        this.messages = new ArrayList<>();
    }

    public void pushMessage(Message message) {
        this.messages.add(message);
    }

    public boolean hasParticipant(String username) {
        return this.initiatorUsername.equals(username) || this.recipientUsername.equals(username);
    }
}
