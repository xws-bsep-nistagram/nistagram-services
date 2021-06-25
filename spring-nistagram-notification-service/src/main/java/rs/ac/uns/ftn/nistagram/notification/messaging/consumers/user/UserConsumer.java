package rs.ac.uns.ftn.nistagram.notification.messaging.consumers.user;

import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.notification.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.notification.messaging.payload.user.UserTopicPayload;
import rs.ac.uns.ftn.nistagram.notification.services.NotificationService;

import java.io.IOException;

@Service
@Slf4j
@AllArgsConstructor
public class UserConsumer {

    NotificationService notificationService;

    @RabbitListener(queues = RabbitMQConfig.USER_BANNED_NOTIFICATION_SERVICE)
    public void consumeUserBanned(UserTopicPayload payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        acknowledgeMessage(channel, tag);
        notificationService.handleUserBanned(payload.getUsername());
    }

    private void acknowledgeMessage(Channel channel, long tag) throws IOException {
        try {
            channel.basicAck(tag, false);
        } catch (Exception e) {
            channel.basicNack(tag, false, true);
        }
    }
}
