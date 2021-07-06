package rs.ac.uns.ftn.nistagram.chat.controllers.payload.responses;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TemporaryMessageResponse extends MessageResponse {

    private String mediaUrl;
    private Boolean opened;

    @Builder
    public TemporaryMessageResponse(Long id, String sender, String receiver, LocalDateTime time,
                                    Boolean seen, String mediaUrl, Boolean opened) {
        super(id, sender, receiver, time, seen);
        this.mediaUrl = mediaUrl;
        this.opened = opened;
    }

}
