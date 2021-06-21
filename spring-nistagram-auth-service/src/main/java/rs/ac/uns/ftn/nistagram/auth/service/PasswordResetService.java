package rs.ac.uns.ftn.nistagram.auth.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@AllArgsConstructor
public class PasswordResetService {

    private final CredentialsRepository credentialsRepository;
    private final PasswordResetRequestRepository passwordResetRepository;
    private final MailService mailService;

    @Transactional
    public void requestPasswordReset(PasswordResetRequest request) {
        log.info("[RESET-PASS-REQ][C][R][CALL={}]", request.getEmail());
        if (!credentialsRepository.existsByEmail(request.getEmail())) {
            throw new PasswordResetException("E-mail doesn't exist!");
        }

        // This will overwrite the existing password reset request (if such an entity exists)
        passwordResetRepository.save(request);
        log.info("[RESET-PASS-REQ][C][C][CALL={}]", request.getEmail());

        mailService.sendPasswordResetMessage(request.getEmail(), request.getUuid());
        log.info("[RESET-PASS-REQ][CALL={}]: Email sent.", request.getEmail());
    }

    @Transactional
    public void resetPassword(PasswordResetBundle bundle) {
        log.info("[RESET-PASS][U][R][UUID={}]", bundle.getUuid());
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
        log.info("[RESET-PASS][U][C][UUID={}]", bundle.getUuid());
    }
}
