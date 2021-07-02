package rs.ac.uns.ftn.nistagram.chat.controllers.payload.responses;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatSessionResponse {

    private Long id;
    private String partner;

}
