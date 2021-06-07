package rs.ac.uns.ftn.nistagram.user.graph.messaging;

import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.mappers.UserPayloadMapper;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.payload.UserTopicPayload;
import rs.ac.uns.ftn.nistagram.user.graph.services.UserService;

import java.io.IOException;

@Service
@Slf4j
@AllArgsConstructor
public class UserConsumer {
    
    private final UserService userService;

    @RabbitListener(queues = RabbitMQConfig.USER_QUEUE)
    public void consume(UserTopicPayload payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        userService.create(UserPayloadMapper.toDomain(payload));
        acknowledgeMessage(channel, tag);

    }

    private void acknowledgeMessage(Channel channel, long tag) throws IOException {
        try{
            channel.basicAck(tag, false);
        }catch (Exception e) {
            channel.basicNack(tag, false, true);
        }
    }

}
