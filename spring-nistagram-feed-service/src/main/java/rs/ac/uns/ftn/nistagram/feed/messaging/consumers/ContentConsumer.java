package rs.ac.uns.ftn.nistagram.feed.messaging.consumers;

import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.feed.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.feed.messaging.mappers.post.PostTopicPayloadMapper;
import rs.ac.uns.ftn.nistagram.feed.messaging.mappers.story.StoryTopicPayloadMapper;
import rs.ac.uns.ftn.nistagram.feed.messaging.payload.post.PostTopicPayload;
import rs.ac.uns.ftn.nistagram.feed.messaging.payload.post.PostPayloadType;
import rs.ac.uns.ftn.nistagram.feed.messaging.payload.story.StoryPayloadType;
import rs.ac.uns.ftn.nistagram.feed.messaging.payload.story.StoryTopicPayload;
import rs.ac.uns.ftn.nistagram.feed.services.FeedService;

import java.io.IOException;

@Service
@Slf4j
@AllArgsConstructor
public class ContentConsumer {

    private final FeedService feedService;

    @RabbitListener(queues = RabbitMQConfig.POST_CREATED_FEED_SERVICE)
    public void consumePostCreated(PostTopicPayload payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{
        acknowledgeMessage(channel, tag);
        if (payload.getPostPayloadType() == PostPayloadType.POST_CREATED)
            feedService.addToPostFeeds(PostTopicPayloadMapper.toPostFeedEntry(payload));
    }

    @RabbitListener(queues = RabbitMQConfig.POST_DELETED_FEED_SERVICE)
    public void consumePostDeleted(PostTopicPayload payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{
        acknowledgeMessage(channel, tag);
        if(payload.getPostPayloadType() == PostPayloadType.POST_DELETED)
            feedService.removeFromPostFeeds(PostTopicPayloadMapper.toPostFeedEntry(payload));
    }

    @RabbitListener(queues = RabbitMQConfig.STORY_CREATED_FEED_SERVICE)
    public void consumeStoryCreated(StoryTopicPayload payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{
        acknowledgeMessage(channel, tag);
        if(payload.getStoryPayloadType() == StoryPayloadType.STORY_CREATED)
            feedService.addToStoryFeeds(StoryTopicPayloadMapper.toStoryFeedEntry(payload));
    }

    @RabbitListener(queues = RabbitMQConfig.STORY_DELETED_FEED_SERVICE)
    public void consumeStoryDeleted(StoryTopicPayload payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{
        acknowledgeMessage(channel, tag);
        if(payload.getStoryPayloadType() == StoryPayloadType.STORY_DELETED)
            feedService.removeFromStoryFeeds(StoryTopicPayloadMapper.toStoryFeedEntry(payload));
    }

    private void acknowledgeMessage(Channel channel, long tag) throws IOException {
        try {
            channel.basicAck(tag, false);
        } catch (Exception e) {
            channel.basicNack(tag, false, true);
        }
    }
}
