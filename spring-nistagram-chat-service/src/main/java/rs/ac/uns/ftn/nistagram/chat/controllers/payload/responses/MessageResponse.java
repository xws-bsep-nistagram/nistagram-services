package rs.ac.uns.ftn.nistagram.chat.controllers.payload.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {

    private Long id;
    private String sender;
    private String receiver;
    private LocalDateTime time;
    private Boolean seen;

}
