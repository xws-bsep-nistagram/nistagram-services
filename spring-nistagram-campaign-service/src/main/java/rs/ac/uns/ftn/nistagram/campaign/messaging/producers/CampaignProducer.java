package rs.ac.uns.ftn.nistagram.campaign.messaging.producers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.campaign.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.campaign.messaging.mapper.TopicPayloadMapper;
import rs.ac.uns.ftn.nistagram.campaign.messaging.payload.TargetedCampaignTopicPayload;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class CampaignProducer {

    private final RabbitTemplate rabbitTemplate;

    public void publishShowCampaigns(List<TargetedCampaignTopicPayload> campaigns) {
        log.info("Campaign show event published to {}",
                RabbitMQConfig.TARGETED_CAMPAIGN_SHOW_FEED_SERVICE);
        rabbitTemplate.convertAndSend(RabbitMQConfig.TARGETED_CAMPAIGN_SHOW_FEED_SERVICE,
                campaigns);
    }

}
