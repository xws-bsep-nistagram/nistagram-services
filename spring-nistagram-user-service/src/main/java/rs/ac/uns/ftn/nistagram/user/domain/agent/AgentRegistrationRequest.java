package rs.ac.uns.ftn.nistagram.user.domain.agent;

import lombok.Getter;
import rs.ac.uns.ftn.nistagram.user.domain.user.User;

import javax.persistence.*;

@Getter
@Entity
public class AgentRegistrationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    private RegistrationRequestStatus requestStatus;
    private String website;

    protected AgentRegistrationRequest() {
    }

    public AgentRegistrationRequest(User user, String website) {
        this.user = user;
        this.website = website;
        this.requestStatus = RegistrationRequestStatus.PENDING;
    }

    public void accept() {
        this.requestStatus = RegistrationRequestStatus.ACCEPTED;
    }

    public void decline() {
        this.requestStatus = RegistrationRequestStatus.DECLINED;
    }
}
