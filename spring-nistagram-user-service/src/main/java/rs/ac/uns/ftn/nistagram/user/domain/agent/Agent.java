package rs.ac.uns.ftn.nistagram.user.domain.agent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import rs.ac.uns.ftn.nistagram.user.domain.user.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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

    protected Agent() {}

}
