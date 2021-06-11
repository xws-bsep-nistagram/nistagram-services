package rs.ac.uns.ftn.nistagram.auth.domain;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

public abstract class MailMessage<T> {

    private final JavaMailSender mailSender;
    private final SimpleMailMessage mailMessage;
    private final T entity;

    public MailMessage(JavaMailSender mailSender, T entity) {
        this.mailMessage = new SimpleMailMessage();
        this.entity = entity;
        this.mailSender = mailSender;
    }

    @Async
    public void send(String from, String to, String subject) throws MailException {
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(formatMessage(entity));
        mailSender.send(mailMessage);
    }

    protected abstract String formatMessage(T entity);

}
