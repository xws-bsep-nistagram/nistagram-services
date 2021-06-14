package rs.ac.uns.ftn.nistagram.auth.domain;

import org.springframework.mail.javamail.JavaMailSender;

public class ActivationMailMessage extends MailMessage<Credentials> {

    private static final String API_HOST = "http://localhost:8080/#/";

    public ActivationMailMessage(JavaMailSender mailSender, Credentials credentials) {
        super(mailSender, credentials);
    }

    private static final String ACTIVATION_FRONT_URL = API_HOST + "activate/";
    @Override
    protected String formatMessage(Credentials credentials) {
        return String.format(
                "Hello, %s,\n\nPlease click the link bellow to activate your account:\n%s",
                credentials.getUsername(), ACTIVATION_FRONT_URL + credentials.getUuid());
    }
}
