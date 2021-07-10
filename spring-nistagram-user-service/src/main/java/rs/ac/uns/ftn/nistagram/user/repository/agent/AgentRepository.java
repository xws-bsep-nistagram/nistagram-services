package rs.ac.uns.ftn.nistagram.user.repository.agent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.nistagram.user.domain.agent.Agent;

public interface AgentRepository extends JpaRepository<Agent, Long> {


    boolean existsByUserUsername(String username);

    @Query(value = "select A from Agent A where A.user.username = :agent")
    Agent getByName(@Param("agent") String agent);
}
