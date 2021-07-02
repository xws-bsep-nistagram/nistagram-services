package rs.ac.uns.ftn.nistagram.chat.domain;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ContentShareMessage extends Message {

    private Long contentId;

    @Builder
    public ContentShareMessage(String sender, String receiver, Long contentId) {
        super(sender, receiver);
        this.contentId = contentId;
    }

}
