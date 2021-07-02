package rs.ac.uns.ftn.nistagram.campaign.service.scheduled;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.campaign.domain.Campaign;
import rs.ac.uns.ftn.nistagram.campaign.domain.LongTermCampaign;
import rs.ac.uns.ftn.nistagram.campaign.domain.OneTimeCampaign;
import rs.ac.uns.ftn.nistagram.campaign.messaging.event.campaign.CampaignsPublishEvent;
import rs.ac.uns.ftn.nistagram.campaign.messaging.payload.CampaignPayload;
import rs.ac.uns.ftn.nistagram.campaign.messaging.util.TransactionIdHolder;
import rs.ac.uns.ftn.nistagram.campaign.service.LongTermCampaignService;
import rs.ac.uns.ftn.nistagram.campaign.service.OneTimeCampaignService;
import rs.ac.uns.ftn.nistagram.campaign.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class ScheduledPublisher {

    private final UserService userService;
    private final LongTermCampaignService longTermCampaignService;
    private final OneTimeCampaignService oneTimeCampaignService;
    private final ApplicationEventPublisher publisher;
    private final TransactionIdHolder transactionIdHolder;

    @Scheduled(cron = "30 * * ? * *")
    public void publishDueCampaigns() {
        List<LongTermCampaign> longTermCampaigns = longTermCampaignService.getDueCampaigns();
        List<OneTimeCampaign> oneTimeCampaigns = oneTimeCampaignService.getDueCampaigns();
        List<Campaign> dueCampaigns = new ArrayList<>(longTermCampaigns);
        dueCampaigns.addAll(oneTimeCampaigns);

        List<CampaignPayload> targetedCampaigns = getTargetedCampaigns(dueCampaigns);
        if (!targetedCampaigns.isEmpty()) {
            CampaignsPublishEvent event = new CampaignsPublishEvent(
                    transactionIdHolder.getCurrentTransactionId(), targetedCampaigns);
            publisher.publishEvent(event);
            log.info("Published {} campaigns to message queue", targetedCampaigns.size());

            longTermCampaigns.forEach(longTermCampaignService::updateCampaignExposuredMoments);
            oneTimeCampaigns.forEach(oneTimeCampaignService::setCampaignExposured);
            log.debug("Updated campaign exposures");
        }
    }

    @Scheduled(cron = "0 0 0 ? * *")
    public void resetLongTermCampaignExposuredMoments() {
        longTermCampaignService.resetCampaignExposuredMoments();
        log.info("Daily reset of active long-term campaign exposure moments complete");
    }

    private List<CampaignPayload> getTargetedCampaigns(List<Campaign> campaigns) {
        return campaigns.stream()
                .map(campaign -> new CampaignPayload(
                        campaign.getId(),
                        userService.findMatchingUsers(campaign),
                        campaign.getType()
                )).collect(Collectors.toList());
    }

}
