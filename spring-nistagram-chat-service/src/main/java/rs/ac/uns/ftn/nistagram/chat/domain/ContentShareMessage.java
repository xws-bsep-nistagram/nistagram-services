package rs.ac.uns.ftn.nistagram.chat.domain;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class ContentShareMessage extends Message {

    private Long contentId;
    private ContentType contentType;

    @Builder
    public ContentShareMessage(String sender, String receiver, Long contentId, ContentType contentType) {
        super(sender, receiver);
        this.contentId = contentId;
        this.contentType = contentType;
    }

    public boolean isStoryReshare() {
        return this.contentType.equals(ContentType.STORY);
    }

    public void removeContentId() {
        this.contentId = null;
    }

    public enum ContentType {
        POST, STORY
    }

}
