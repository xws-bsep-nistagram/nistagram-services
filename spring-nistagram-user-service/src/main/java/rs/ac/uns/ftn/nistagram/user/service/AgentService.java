package rs.ac.uns.ftn.nistagram.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.user.domain.agent.Agent;
import rs.ac.uns.ftn.nistagram.user.domain.agent.AgentRegistrationRequest;
import rs.ac.uns.ftn.nistagram.user.domain.user.User;
import rs.ac.uns.ftn.nistagram.user.http.auth.AuthClient;
import rs.ac.uns.ftn.nistagram.user.infrastructure.exceptions.UserException;
import rs.ac.uns.ftn.nistagram.user.repository.agent.AgentRegistrationRequestRepository;
import rs.ac.uns.ftn.nistagram.user.repository.agent.AgentRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class AgentService {

    private final AgentRegistrationRequestRepository requestRepository;
    private final AgentRepository agentRepository;
    private final ProfileService profileService;
    private final AuthClient authClient;

    @Transactional
    public AgentRegistrationRequest createRegistrationRequest(AgentRegistrationRequest newRequest) {
        Objects.requireNonNull(newRequest);
        Objects.requireNonNull(newRequest.getUser());
        if (newRequest.getId() != null && requestRepository.existsById(newRequest.getId())) {
            throw new UserException("Agent registration request already exists!");
        }
        if (requestRepository.existsByUserUsername(newRequest.getUser().getUsername())) {
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

    @Transactional(readOnly = true)
    public List<AgentRegistrationRequest> getAllPending() {
        List<AgentRegistrationRequest> pendingRequests = requestRepository.getAllPending();
        log.info("Found {} pending requests", pendingRequests.size());
        return pendingRequests;
    }

    @Transactional
    public AgentRegistrationRequest decline(String username) {
        if (!requestRepository.existsByUserUsername(username))
            throw new UserException("Agent registration request doesn't exist!");

        AgentRegistrationRequest found = requestRepository.findByUsername(username);
        found.decline();
        requestRepository.save(found);

        log.info("Agent registration request for an user '{}' declined", username);

        return found;
    }

    @Transactional
    public Agent accept(String username) {
        if (!requestRepository.existsByUserUsername(username))
            throw new UserException("Agent registration request doesn't exist!");

        AgentRegistrationRequest foundRequest = requestRepository.findByUsername(username);
        foundRequest.accept();
        requestRepository.save(foundRequest);

        Agent agent = new Agent(foundRequest);
        agentRepository.save(agent);

        authClient.registerAgent(username);

        log.info("Agent registration request for an user '{}' accepted", username);

        return agent;
    }

    @Transactional
    public Agent promoteToAgent(String username, String website) {
        User user = profileService.get(username);

        Agent agent = new Agent(user, website);
        agentRepository.save(agent);

        authClient.registerAgent(username);

        log.info("User '{}' promoted to agent", username);

        return agent;

    }

    @Transactional
    public List<User> getNonPromoted() {
        List<User> users = profileService.getAll();
        users = users
                .stream()
                .filter(user -> !requestRepository.existsByUserUsername(user.getUsername()) &&
                        !agentRepository.existsByUserUsername(user.getUsername()))
                .collect(Collectors.toList());

        return users;
    }

}
