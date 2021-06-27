package rs.ac.uns.ftn.nistagram.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.user.domain.agent.AgentRegistrationRequest;
import rs.ac.uns.ftn.nistagram.user.domain.user.User;
import rs.ac.uns.ftn.nistagram.user.infrastructure.exceptions.UserException;
import rs.ac.uns.ftn.nistagram.user.repository.agent.AgentRegistrationRequestRepository;
import rs.ac.uns.ftn.nistagram.user.repository.agent.AgentRepository;

import java.util.Objects;

@Slf4j
@AllArgsConstructor
@Service
public class AgentService {

    private final AgentRegistrationRequestRepository requestRepository;
    private final AgentRepository agentRepository;
    private final ProfileService profileService;

    @Transactional
    public AgentRegistrationRequest createRegistrationRequest(AgentRegistrationRequest newRequest) {
        Objects.requireNonNull(newRequest);
        Objects.requireNonNull(newRequest.getUser());
        if (newRequest.getId() != null && requestRepository.existsById(newRequest.getId())) {
            throw new UserException("Agent registration request already exists!");
        }
        if(requestRepository.existsByUserUsername(newRequest.getUser().getUsername())) {
            throw new UserException("Agent registration request already exists!");
        }
        AgentRegistrationRequest created = requestRepository.save(newRequest);
        log.info("Agent registration request created for user '{}'", newRequest.getUser().getUsername());
        return created;
    }

    @Transactional
    public void registerRequest(String username, String website) {
        User found = profileService.get(username);
        AgentRegistrationRequest newRequest = new AgentRegistrationRequest(found, website);
        createRegistrationRequest(newRequest);
    }

}
