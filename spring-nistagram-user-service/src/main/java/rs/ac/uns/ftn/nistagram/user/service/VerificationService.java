package rs.ac.uns.ftn.nistagram.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.user.domain.user.User;
import rs.ac.uns.ftn.nistagram.user.domain.user.request.VerificationRequest;
import rs.ac.uns.ftn.nistagram.user.infrastructure.exceptions.UserException;
import rs.ac.uns.ftn.nistagram.user.repository.VerificationRequestRepository;

import javax.persistence.EntityExistsException;

@Slf4j
@AllArgsConstructor
@Service
public class VerificationService {

    private final VerificationRequestRepository repository;
    private final ProfileService profileService;

    @Transactional
    public VerificationRequest create(String username, VerificationRequest request) {
        if (repository.existsByUserUsername(username)) {
            throw new UserException("Verification request has already been registered!");
        }
        User requesting = profileService.get(username);
        request.setUser(requesting);
        VerificationRequest created = repository.save(request);

        log.info("Created verification request with id {} for user '{}'", created.getId(), username);
        return created;
    }

}
