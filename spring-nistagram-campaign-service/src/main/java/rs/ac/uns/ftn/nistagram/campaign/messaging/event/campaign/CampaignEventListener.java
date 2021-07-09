package rs.ac.uns.ftn.nistagram.campaign.messaging.event.campaign;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.campaign.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.campaign.messaging.util.Converter;

@Slf4j
@Component
@AllArgsConstructor
public class CampaignEventListener {

    private final RabbitTemplate rabbitTemplate;
    private final Converter converter;

    @Async
    @EventListener
    public void onCampaignsPublish(CampaignsPublishEvent event) {
        log.info("Sending campaigns publish event to {}, event: {}", RabbitMQConfig.CAMPAIGNS_PUBLISH_EVENT, event);

        rabbitTemplate.convertAndSend(RabbitMQConfig.CAMPAIGNS_PUBLISH_EVENT, converter.toJSON(event));
    }

    @Async
    @EventListener
    public void onCampaignDelete(CampaignDeleteEvent event) {
        log.info("Sending campaign delete event to {}, event: {}", RabbitMQConfig.CAMPAIGN_DELETE_EVENT, event);

        rabbitTemplate.convertAndSend(RabbitMQConfig.CAMPAIGN_DELETE_EVENT, converter.toJSON(event));
    }

}
