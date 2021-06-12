package rs.ac.uns.ftn.nistagram.auth.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.auth.domain.Credentials;
import rs.ac.uns.ftn.nistagram.auth.domain.PasswordResetBundle;
import rs.ac.uns.ftn.nistagram.auth.domain.PasswordResetRequest;
import rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions.PasswordResetException;
import rs.ac.uns.ftn.nistagram.auth.repository.CredentialsRepository;
import rs.ac.uns.ftn.nistagram.auth.repository.PasswordResetRequestRepository;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PasswordResetService {

    private final CredentialsRepository credentialsRepository;
    private final PasswordResetRequestRepository passwordResetRepository;
    private final MailService mailService;

    @Transactional
    public void requestPasswordReset(PasswordResetRequest request) {
        if (!credentialsRepository.existsByEmail(request.getEmail())) {
            throw new PasswordResetException("E-mail doesn't exist!");
        }
        passwordResetRepository.save(request);
        mailService.sendPasswordResetMessage(request.getEmail(), request.getUuid());
    }

    @Transactional
    public void resetPassword(PasswordResetBundle bundle) {
        // This throws if there is no such request (with such a UUID)
        PasswordResetRequest request = passwordResetRepository.findByUUID(bundle.getUuid()).orElseThrow();
        // This throws if there is no user to be found with such an email
        Credentials toBeChanged = credentialsRepository.findByEmail(request.getEmail()).orElseThrow();

        if (request.isReset())
            throw new PasswordResetException("You already reset your password using this form.");
        if (request.getExpires().isBefore(LocalDateTime.now()))
            throw new PasswordResetException("Your password reset timer has run out.");

        toBeChanged.setPasswordHash(bundle.getPassword());
        credentialsRepository.save(toBeChanged);

        request.triggerExpiration();
        passwordResetRepository.save(request);
    }

}
