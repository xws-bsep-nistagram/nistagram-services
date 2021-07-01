package rs.ac.uns.ftn.nistagram.user.messaging.eventhandler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.user.http.auth.AuthClient;
import rs.ac.uns.ftn.nistagram.user.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.user.messaging.event.RegistrationFailedEvent;
import rs.ac.uns.ftn.nistagram.user.messaging.util.Converter;
import rs.ac.uns.ftn.nistagram.user.service.ProfileService;


@Slf4j
@Component
@AllArgsConstructor
public class UserEventHandler {

    private final ProfileService profileService;
    private final AuthClient authClient;
    private final Converter converter;

    @RabbitListener(queues = {RabbitMQConfig.REGISTRATION_FAILED_EVENT})
    public void handleRegistrationFailed(@Payload String payload) {

        log.info("Handling a registration failed event {}", payload);

        RegistrationFailedEvent event = converter.toObject(payload, RegistrationFailedEvent.class);

        profileService.delete(event.getUsername());

        authClient.delete(event.getUsername());

    }


}
