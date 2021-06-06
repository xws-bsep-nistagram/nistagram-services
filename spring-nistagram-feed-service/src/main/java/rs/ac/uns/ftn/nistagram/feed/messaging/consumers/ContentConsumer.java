package rs.ac.uns.ftn.nistagram.feed.messaging.consumers;

import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.feed.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.feed.messaging.mappers.content.ContentTopicPayloadMapper;
import rs.ac.uns.ftn.nistagram.feed.messaging.payload.content.ContentTopicPayload;
import rs.ac.uns.ftn.nistagram.feed.messaging.payload.content.PayloadType;
import rs.ac.uns.ftn.nistagram.feed.services.FeedService;

import java.io.IOException;

@Service
@Slf4j
@AllArgsConstructor
public class ContentConsumer {

    private final FeedService feedService;

    @RabbitListener(queues = RabbitMQConfig.CONTENT_CREATED_FEED_SERVICE)
    public void consume(ContentTopicPayload payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{
        if (payload.getPayloadType() == PayloadType.POST_CREATED)
            feedService.addToFeeds(ContentTopicPayloadMapper.toPostFeedEntry(payload));
        acknowledgeMessage(channel, tag);
    }

    private void acknowledgeMessage(Channel channel, long tag) throws IOException {
        try {
            channel.basicAck(tag, false);
        } catch (Exception e) {
            channel.basicNack(tag, false, true);
        }
    }
}
