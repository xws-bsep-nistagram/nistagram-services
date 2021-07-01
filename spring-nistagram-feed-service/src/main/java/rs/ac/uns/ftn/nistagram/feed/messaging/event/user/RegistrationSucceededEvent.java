package rs.ac.uns.ftn.nistagram.feed.messaging.event.user;

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
