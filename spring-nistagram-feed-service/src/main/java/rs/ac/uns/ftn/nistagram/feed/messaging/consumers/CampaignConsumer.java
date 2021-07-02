package rs.ac.uns.ftn.nistagram.feed.messaging.consumers;

import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.feed.http.payload.user.UserPayload;
import rs.ac.uns.ftn.nistagram.feed.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.feed.messaging.mappers.campaign.CampaignTopicPayloadMapper;
import rs.ac.uns.ftn.nistagram.feed.messaging.payload.campaign.CampaignType;
import rs.ac.uns.ftn.nistagram.feed.messaging.payload.campaign.TargetedCampaignTopicPayload;
import rs.ac.uns.ftn.nistagram.feed.services.FeedService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class CampaignConsumer {

    private final FeedService service;

    @RabbitListener(queues = RabbitMQConfig.TARGETED_CAMPAIGN_SHOW_FEED_SERVICE)
    public void consumePostCreated(List<TargetedCampaignTopicPayload> payloadList, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        acknowledgeMessage(channel, tag);
        log.info("Consumed {} campaigns for showing", payloadList.size());
        payloadList.forEach(payload -> {
            if (payload.getType() == CampaignType.POST) {
                List<UserPayload> users = payload.getUsers().stream()
                        .map(UserPayload::new)
                        .collect(Collectors.toList());
                service.addCampaignToPostFeeds(users, CampaignTopicPayloadMapper.toPost(payload));
            }
            else if (payload.getType() == CampaignType.STORY) {
                List<UserPayload> users = payload.getUsers().stream()
                        .map(UserPayload::new)
                        .collect(Collectors.toList());
                service.addCampaignToStoryFeeds(users, CampaignTopicPayloadMapper.toStory(payload));
            }
        });

    }

    private void acknowledgeMessage(Channel channel, long tag) throws IOException {
        try {
            channel.basicAck(tag, false);
        } catch (Exception e) {
            channel.basicNack(tag, false, true);
        }
    }

}
