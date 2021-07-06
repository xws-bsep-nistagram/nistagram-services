package rs.ac.uns.ftn.nistagram.chat.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class TextMessage extends Message {

    private String text;

    @Builder
    public TextMessage(String sender, String receiver, String text) {
        super(sender, receiver);
        this.text = text;
    }

}
