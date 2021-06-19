package rs.ac.uns.ftn.nistagram.user.graph.messaging.consumers;

import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.mappers.TopicPayloadMapper;
import rs.ac.uns.ftn.nistagram.user.graph.messaging.payload.user.UserTopicPayload;
import rs.ac.uns.ftn.nistagram.user.graph.services.UserService;

import java.io.IOException;

@Service
@Slf4j
@AllArgsConstructor
public class UserConsumer {

    private final UserService userService;

    @RabbitListener(queues = RabbitMQConfig.USER_CREATED_GRAPH_SERVICE)
    public void consumeUserCreated(UserTopicPayload payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        acknowledgeMessage(channel, tag);
        userService.create(TopicPayloadMapper.toDomain(payload));
    }

    @RabbitListener(queues = RabbitMQConfig.USER_UPDATED_GRAPH_SERVICE)
    public void consumeUserUpdated(UserTopicPayload payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        acknowledgeMessage(channel, tag);
        userService.update(TopicPayloadMapper.toDomain(payload));
    }

    private void acknowledgeMessage(Channel channel, long tag) throws IOException {
        try {
            channel.basicAck(tag, false);
        } catch (Exception e) {
            channel.basicNack(tag, false, true);
        }
    }

}
