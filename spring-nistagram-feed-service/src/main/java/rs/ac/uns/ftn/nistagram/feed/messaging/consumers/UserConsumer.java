package rs.ac.uns.ftn.nistagram.feed.messaging.consumers;

import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.feed.services.UserService;
import rs.ac.uns.ftn.nistagram.feed.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.feed.messaging.mappers.user.UserTopicPayloadMapper;
import rs.ac.uns.ftn.nistagram.feed.messaging.payload.user.UserTopicPayload;

import java.io.IOException;

@Service
@Slf4j
@AllArgsConstructor
public class UserConsumer {
    
    private final UserService userService;

    @RabbitListener(queues = RabbitMQConfig.USER_CREATED_FEED_SERVICE)
    public void consume(UserTopicPayload payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        acknowledgeMessage(channel, tag);
        userService.create(UserTopicPayloadMapper.toDomain(payload));
    }

    private void acknowledgeMessage(Channel channel, long tag) throws IOException {
        try{
            channel.basicAck(tag, false);
        }catch (Exception e) {
            channel.basicNack(tag, false, true);
        }
    }

}
