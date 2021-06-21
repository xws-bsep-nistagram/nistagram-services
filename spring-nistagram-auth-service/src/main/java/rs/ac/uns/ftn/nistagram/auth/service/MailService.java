package rs.ac.uns.ftn.nistagram.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.auth.domain.ActivationMailMessage;
import rs.ac.uns.ftn.nistagram.auth.domain.Credentials;
import rs.ac.uns.ftn.nistagram.auth.domain.PasswordResetMailMessage;
import rs.ac.uns.ftn.nistagram.auth.domain.PasswordResetRequest;

@Service
public class MailService {

    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String SENDER_EMAIL;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendActivationMessage(Credentials credentials) {
        ActivationMailMessage message = new ActivationMailMessage(mailSender, credentials);
        message.send(SENDER_EMAIL, credentials.getEmail(), "Activate your account");
    }

    public void sendPasswordResetMessage(String email, String uuid) {
        PasswordResetMailMessage message = new PasswordResetMailMessage(mailSender, uuid);
        message.send(SENDER_EMAIL, email, "Password reset");
    }

}
