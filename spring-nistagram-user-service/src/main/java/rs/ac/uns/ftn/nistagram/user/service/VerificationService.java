package rs.ac.uns.ftn.nistagram.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.user.domain.user.User;
import rs.ac.uns.ftn.nistagram.user.domain.verification.VerificationRequest;
import rs.ac.uns.ftn.nistagram.user.domain.verification.VerificationStatus;
import rs.ac.uns.ftn.nistagram.user.infrastructure.exceptions.UserException;
import rs.ac.uns.ftn.nistagram.user.repository.VerificationRequestRepository;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class VerificationService {

    private final VerificationRequestRepository repository;
    private final CategoryService categoryService;
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

    @Transactional(readOnly = true)
    public List<VerificationRequest> getPending() {
        return repository.findByStatus(VerificationStatus.PENDING);
    }

    @Transactional(readOnly = true)
    public VerificationRequest get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserException("Verification request doesn't exist!"));
    }

    @Transactional
    public void verify(Long id) {
        VerificationRequest found = get(id);
        if (found.getStatus() != VerificationStatus.PENDING) {
            throw new UserException("Cannot decline request that isn't pending.");
        }
        String username = found.getUser().getUsername();
        profileService.verify(username);
        found.setStatus(VerificationStatus.ACCEPTED);
        repository.save(found);
        log.info("Accepted verification request from user '{}'", username);
    }

    @Transactional
    public void decline(Long id) {
        VerificationRequest found = get(id);
        if (found.getStatus() != VerificationStatus.PENDING) {
            throw new UserException("Cannot decline request that isn't pending.");
        }
        found.setStatus(VerificationStatus.DECLINED);
        repository.save(found);
        log.info("Declined verification request from user '{}'", found.getUser().getUsername());
    }
}
