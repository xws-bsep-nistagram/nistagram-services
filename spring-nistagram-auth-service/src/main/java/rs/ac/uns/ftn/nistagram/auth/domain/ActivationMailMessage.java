package rs.ac.uns.ftn.nistagram.auth.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;

public class ActivationMailMessage extends MailMessage<Credentials> {

    @Value("api.gateway.host")
    private String API_HOST;

    public ActivationMailMessage(JavaMailSender mailSender, Credentials credentials) {
        super(mailSender, credentials);
    }

    @Override
    protected String formatMessage(Credentials credentials) {
        String url = API_HOST + "/api/auth/activate/" + credentials.getUuid();
        String message = String.format(
                "Hello, %s,\n\nPlease click the link bellow to activate your account:\n%s",
                credentials.getUsername(), url);
        return message;
    }

}
