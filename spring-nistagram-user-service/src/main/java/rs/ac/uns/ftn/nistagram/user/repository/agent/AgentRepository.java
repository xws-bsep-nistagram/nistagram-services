package rs.ac.uns.ftn.nistagram.user.repository.agent;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.nistagram.user.domain.agent.Agent;

public interface AgentRepository extends JpaRepository<Agent, Long> {


    boolean existsByUserUsername(String username);
}
