package rs.ac.uns.ftn.nistagram.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.auth.domain.PasswordResetRequest;
import rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions.PasswordResetException;
import rs.ac.uns.ftn.nistagram.auth.repository.CredentialsRepository;
import rs.ac.uns.ftn.nistagram.auth.repository.PasswordResetRequestRepository;

@Service
public class PasswordResetService {

    private final CredentialsRepository credentialsRepository;
    private final PasswordResetRequestRepository passwordResetRepository;
    private final MailService mailService;

    public PasswordResetService(CredentialsRepository credentialsRepository, PasswordResetRequestRepository passwordResetRepository, MailService mailService) {
        this.credentialsRepository = credentialsRepository;
        this.passwordResetRepository = passwordResetRepository;
        this.mailService = mailService;
    }

    @Transactional
    public void requestPasswordReset(PasswordResetRequest request) {
        if (!credentialsRepository.existsByEmail(request.getEmail())) {
            throw new PasswordResetException("E-mail doesn't exist!");
        }
        passwordResetRepository.save(request);
        mailService.sendPasswordResetMessage(request);
    }

}
