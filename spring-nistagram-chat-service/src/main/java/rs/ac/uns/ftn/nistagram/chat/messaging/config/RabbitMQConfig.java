package rs.ac.uns.ftn.nistagram.chat.messaging.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String MESSAGE_REQUEST_RECEIVED_EVENT = "MESSAGE_REQUEST_RECEIVED_EVENT";
    public static final String MESSAGE_RECEIVED_EVENT = "MESSAGE_RECEIVED_EVENT";

}
