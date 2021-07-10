package rs.ac.uns.ftn.nistagram.user.messaging.event;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RegistrationSucceededEvent {

    private String transactionId;

    private String username;

}
