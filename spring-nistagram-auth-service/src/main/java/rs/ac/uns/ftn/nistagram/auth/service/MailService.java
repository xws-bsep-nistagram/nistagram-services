package rs.ac.uns.ftn.nistagram.auth.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.auth.domain.ActivationMailMessage;
import rs.ac.uns.ftn.nistagram.auth.domain.Credentials;
import rs.ac.uns.ftn.nistagram.auth.domain.PasswordResetMailMessage;
import rs.ac.uns.ftn.nistagram.auth.domain.PasswordResetRequest;

@Service
public class MailService {

    private final JavaMailSender mailSender;
    private static final String SENDER_EMAIL = "nistagram.info@gmail.com";

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendActivationMessage(Credentials credentials) {
        ActivationMailMessage message = new ActivationMailMessage(mailSender, credentials);
        message.send(SENDER_EMAIL, credentials.getEmail(), "Activate your account");
    }

    public void sendPasswordResetMessage(PasswordResetRequest request) {
        PasswordResetMailMessage message = new PasswordResetMailMessage(mailSender, request.getUuid());
        message.send(SENDER_EMAIL, request.getEmail(), "Password reset");
    }

}
