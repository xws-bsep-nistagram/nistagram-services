package rs.ac.uns.ftn.nistagram.chat.controllers.payload.responses;

import lombok.*;
import rs.ac.uns.ftn.nistagram.chat.domain.ContentShareMessage;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContentShareMessageResponse extends MessageResponse {

    private Long contentId;
    private ContentShareMessage.ContentType contentType;

    @Builder
    public ContentShareMessageResponse(Long id, String sender, String receiver, LocalDateTime time,
                                       ContentShareMessage.ContentType contentType, Boolean seen, Long contentId) {
        super(id, sender, receiver, time, seen);
        this.contentId = contentId;
        this.contentType = contentType;
    }

}
