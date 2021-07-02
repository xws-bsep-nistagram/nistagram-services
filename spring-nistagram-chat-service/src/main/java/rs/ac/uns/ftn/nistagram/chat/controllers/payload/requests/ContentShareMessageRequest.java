package rs.ac.uns.ftn.nistagram.chat.controllers.payload.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ContentShareMessageRequest extends MessageRequest {

    @NotNull
    private Long contentId;

}
