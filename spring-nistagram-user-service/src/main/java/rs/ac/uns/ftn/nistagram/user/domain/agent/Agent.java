package rs.ac.uns.ftn.nistagram.user.domain.agent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import rs.ac.uns.ftn.nistagram.user.domain.user.User;

import javax.persistence.*;

@AllArgsConstructor
@Getter
@Entity
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    private String website;

    protected Agent() {
    }

    public Agent(AgentRegistrationRequest request) {
        this.user = request.getUser();
        this.website = request.getWebsite();
    }

    public Agent(User user, String website) {
        this.user = user;
        this.website = website;
    }
}
