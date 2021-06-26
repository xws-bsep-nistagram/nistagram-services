package rs.ac.uns.ftn.nistagram.user.repository.agent;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.nistagram.user.domain.agent.AgentRegistrationRequest;

public interface AgentRegistrationRequestRepository extends JpaRepository<AgentRegistrationRequest, Long> {

    boolean existsByUserUsername(String username);

}
