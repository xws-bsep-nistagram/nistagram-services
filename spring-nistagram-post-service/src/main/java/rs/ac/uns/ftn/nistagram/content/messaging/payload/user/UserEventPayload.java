package rs.ac.uns.ftn.nistagram.content.messaging.payload.user;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEventPayload {

    private String username;

}
