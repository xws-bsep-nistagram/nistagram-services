package rs.ac.uns.ftn.nistagram.campaign.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.campaign.domain.OneTimeCampaign;
import rs.ac.uns.ftn.nistagram.campaign.repository.OneTimeCampaignRepository;

@Service
public class OneTimeCampaignService extends CampaignService<OneTimeCampaign> {

    public OneTimeCampaignService(OneTimeCampaignRepository repository) {
        super(repository);
    }

}
