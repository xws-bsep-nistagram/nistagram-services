package rs.ac.uns.ftn.nistagram.chat.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String sender;
    private String receiver;
    private LocalDateTime time;
    private Boolean seen;

    public Message(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.seen = false;
    }

    public boolean hasReceiver(String caller) {
        return this.receiver.equals(caller);
    }

    public void markAsSeen() {
        this.seen = true;
    }
}
