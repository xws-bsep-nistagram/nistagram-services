package rs.ac.uns.ftn.nistagram.campaign.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.campaign.domain.LongTermCampaign;
import rs.ac.uns.ftn.nistagram.campaign.repository.LongTermCampaignRepository;

@Service
public class LongTermCampaignService extends CampaignService<LongTermCampaign> {

    public LongTermCampaignService(LongTermCampaignRepository repository) {
        super(repository);
    }

}
