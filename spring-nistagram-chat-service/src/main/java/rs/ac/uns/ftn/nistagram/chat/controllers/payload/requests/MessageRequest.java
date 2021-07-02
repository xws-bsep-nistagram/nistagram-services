package rs.ac.uns.ftn.nistagram.chat.controllers.payload.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MessageRequest {

    @NotBlank
    private String sender;
    @NotBlank
    private String receiver;

}
