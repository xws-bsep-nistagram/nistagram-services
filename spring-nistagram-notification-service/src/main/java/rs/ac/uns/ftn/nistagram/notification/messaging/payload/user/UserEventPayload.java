package rs.ac.uns.ftn.nistagram.notification.messaging.payload.user;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEventPayload {

    private String username;

}
