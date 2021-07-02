package rs.ac.uns.ftn.nistagram.feed.messaging.eventhandler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.feed.http.payload.user.UserPayload;
import rs.ac.uns.ftn.nistagram.feed.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.feed.messaging.event.campaign.CampaignsPublishEvent;
import rs.ac.uns.ftn.nistagram.feed.messaging.mappers.campaign.CampaignPayloadMapper;
import rs.ac.uns.ftn.nistagram.feed.messaging.payload.campaign.CampaignType;
import rs.ac.uns.ftn.nistagram.feed.messaging.util.Converter;
import rs.ac.uns.ftn.nistagram.feed.services.FeedService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class CampaignEventHandler {

    private final FeedService service;
    private final Converter converter;

    @RabbitListener(queues = RabbitMQConfig.CAMPAIGNS_PUBLISH_EVENT)
    public void handleCampaignsPublish(@Payload String payload) {
        log.debug("Handling campaigns publish event {}", payload);

        CampaignsPublishEvent event = converter.toObject(payload, CampaignsPublishEvent.class);

        log.info("Received {} campaigns for publishing", event.getPayload().size());

        event.getPayload().forEach(campaignPayload -> {
            if (campaignPayload.getType() == CampaignType.POST) {
                List<UserPayload> users = campaignPayload.getUsers().stream()
                        .map(UserPayload::new)
                        .collect(Collectors.toList());
                service.addCampaignToPostFeeds(users, CampaignPayloadMapper.toPost(campaignPayload));
            } else if (campaignPayload.getType() == CampaignType.STORY) {
                List<UserPayload> users = campaignPayload.getUsers().stream()
                        .map(UserPayload::new)
                        .collect(Collectors.toList());
                service.addCampaignToStoryFeeds(users, CampaignPayloadMapper.toStory(campaignPayload));
            }
        });
    }

}
