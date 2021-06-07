package rs.ac.uns.ftn.nistagram.user.messaging;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.user.domain.user.User;
import rs.ac.uns.ftn.nistagram.user.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.user.messaging.mappers.UserPayloadMapper;

@Service
@Slf4j
@AllArgsConstructor
public class UserProducer {

    private final RabbitTemplate rabbitTemplate;

    public void publishCreatedUser(User user){
        log.info("User with an username {} published to a {} ",
                user.getUsername(), RabbitMQConfig.USER_QUEUE);

        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_QUEUE,
                UserPayloadMapper.toPayload(user));
    }


}
