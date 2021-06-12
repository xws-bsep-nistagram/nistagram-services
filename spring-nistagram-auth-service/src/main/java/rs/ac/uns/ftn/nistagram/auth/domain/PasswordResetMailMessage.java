package rs.ac.uns.ftn.nistagram.auth.domain;

import org.springframework.mail.javamail.JavaMailSender;

public class PasswordResetMailMessage extends MailMessage<String> {

    private static final String FRONT_HOST = "http://localhost:8080/password-reset/";

    public PasswordResetMailMessage(JavaMailSender mailSender, String uuid) {
        super(mailSender, uuid);
    }

    @Override
    protected String formatMessage(String uuid) {
        return "Please follow the link bellow to reset your password:\n" + FRONT_HOST + uuid;
    }
}
