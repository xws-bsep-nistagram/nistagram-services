package rs.ac.uns.ftn.nistagram.user.graph.messaging.payload.user;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserEventPayload {

    private String username;
    private PrivacyData privacyData;

}
