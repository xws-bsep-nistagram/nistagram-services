package rs.ac.uns.ftn.nistagram.user.repository.agent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.nistagram.user.domain.agent.AgentRegistrationRequest;

import java.util.List;

public interface AgentRegistrationRequestRepository extends JpaRepository<AgentRegistrationRequest, Long> {

    boolean existsByUserUsername(String username);

    @Query("select request from AgentRegistrationRequest request where request.requestStatus = 2 ")
    List<AgentRegistrationRequest> getAllPending();

    @Query("select request from AgentRegistrationRequest request join request.user user where user.username=:username")
    AgentRegistrationRequest findByUsername(String username);

    @Query("select request from AgentRegistrationRequest request " +
            "join request.user user where user.username =:username " +
            "and request.requestStatus != 0")
    List<AgentRegistrationRequest> findNonAcceptedByUsername(String username);
}
