package rs.ac.uns.ftn.nistagram.user.graph.messaging.payload.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdministrativeData {

    private boolean verified;
    private boolean banned;

}
