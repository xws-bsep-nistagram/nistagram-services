package rs.ac.uns.ftn.nistagram.chat.controllers.payload.responses;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContentShareMessageResponse extends MessageResponse {

    private Long contentId;

    @Builder
    public ContentShareMessageResponse(Long id, String sender, String receiver,
                                       LocalDateTime time, Boolean seen, Long contentId) {
        super(id, sender, receiver, time, seen);
        this.contentId = contentId;
    }

}
