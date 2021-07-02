package rs.ac.uns.ftn.nistagram.chat.controllers.payload.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TemporaryMediaMessageRequest extends MessageRequest {

    @NotBlank
    private String mediaUrl;

}
