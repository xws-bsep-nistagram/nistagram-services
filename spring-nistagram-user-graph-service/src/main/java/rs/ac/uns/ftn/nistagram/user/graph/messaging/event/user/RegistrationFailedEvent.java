package rs.ac.uns.ftn.nistagram.user.graph.messaging.event.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RegistrationFailedEvent {

    private String transactionId;

    private String username;

}
