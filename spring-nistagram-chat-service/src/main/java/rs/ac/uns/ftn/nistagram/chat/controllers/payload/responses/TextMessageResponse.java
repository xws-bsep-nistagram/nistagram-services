package rs.ac.uns.ftn.nistagram.chat.controllers.payload.responses;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TextMessageResponse extends MessageResponse {

    private String text;

    @Builder
    public TextMessageResponse(Long id, String sender, String receiver, LocalDateTime time,
                               Boolean seen, String text) {
        super(id, sender, receiver, time, seen);
        this.text = text;
    }

}
