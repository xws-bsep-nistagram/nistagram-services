package rs.ac.uns.ftn.nistagram.chat.controllers.payload.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class TextMessageRequest extends MessageRequest {

    @NotBlank
    private String text;

}
