package rs.ac.uns.ftn.nistagram.campaign.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.campaign.domain.Campaign;
import rs.ac.uns.ftn.nistagram.campaign.domain.OneTimeCampaign;
import rs.ac.uns.ftn.nistagram.campaign.http.PostClient;
import rs.ac.uns.ftn.nistagram.campaign.messaging.util.TransactionIdHolder;
import rs.ac.uns.ftn.nistagram.campaign.repository.OneTimeCampaignRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class OneTimeCampaignService extends CampaignService<OneTimeCampaign> {

    private final OneTimeCampaignRepository repository;

    public OneTimeCampaignService(
            OneTimeCampaignRepository repository,
            PostClient postClient,
            ApplicationEventPublisher publisher,
            TransactionIdHolder transactionIdHolder) {
        super(repository, postClient, publisher, transactionIdHolder);
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<OneTimeCampaign> getDueCampaigns() {
        LocalDateTime now = LocalDateTime.now();
        List<OneTimeCampaign> dueCampaigns = repository.findNonExposured(now);
        log.info("Fetched {} campaigns due for showing", dueCampaigns.size());
        return dueCampaigns;
    }

    @Transactional
    public void setCampaignExposured(OneTimeCampaign campaign) {
        Objects.requireNonNull(campaign);
        campaign.setExposured(true);
        repository.save(campaign);
    }

}
