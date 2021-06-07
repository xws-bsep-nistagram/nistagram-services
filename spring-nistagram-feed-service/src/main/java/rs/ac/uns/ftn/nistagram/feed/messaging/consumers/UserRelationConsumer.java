package rs.ac.uns.ftn.nistagram.feed.messaging.consumers;

import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.feed.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.feed.messaging.payload.user.relations.UserRelationshipRequest;
import rs.ac.uns.ftn.nistagram.feed.services.FeedService;

import java.io.IOException;

@Service
@Slf4j
@AllArgsConstructor
public class UserRelationConsumer {

    private final FeedService feedService;

    @RabbitListener(queues = RabbitMQConfig.USER_FOLLOWED_FEED_SERVICE)
    public void consumeUserFollowed(UserRelationshipRequest payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        acknowledgeMessage(channel, tag);
        feedService.addTargetsContent(payload.getSubject(), payload.getTarget());
    }

    @RabbitListener(queues = RabbitMQConfig.USER_UNFOLLOWED_FEED_SERVICE)
    public void consumeUserUnfollowed(UserRelationshipRequest payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        acknowledgeMessage(channel, tag);
        feedService.removeTargetsContent(payload.getSubject(), payload.getTarget());
    }


    private void acknowledgeMessage(Channel channel, long tag) throws IOException {
        try {
            channel.basicAck(tag, false);
        } catch (Exception e) {
            channel.basicNack(tag, false, true);
        }
    }
}
