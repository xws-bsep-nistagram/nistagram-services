package rs.ac.uns.ftn.nistagram.user.domain.agent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import rs.ac.uns.ftn.nistagram.user.domain.user.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Getter
@Entity
public class AgentRegistrationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    private String website;

    protected AgentRegistrationRequest() {}

    public AgentRegistrationRequest(User user, String website) {
        this.user = user;
        this.website = website;
    }
}
