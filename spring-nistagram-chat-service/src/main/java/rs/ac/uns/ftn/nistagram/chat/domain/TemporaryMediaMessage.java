package rs.ac.uns.ftn.nistagram.chat.domain;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TemporaryMediaMessage extends Message {

    private boolean opened;
    private String mediaUrl;

    @Builder
    public TemporaryMediaMessage(String sender, String receiver, String mediaUrl) {
        super(sender, receiver);
        this.mediaUrl = mediaUrl;
        this.opened = false;
    }

    public void markAsOpened() {
        this.opened = true;
    }
}
