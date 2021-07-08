package rs.ac.uns.ftn.nistagram.campaign.controller.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.campaign.controller.dto.LongTermCampaignViewDTO;
import rs.ac.uns.ftn.nistagram.campaign.controller.dto.OneTimeCampaignViewDTO;
import rs.ac.uns.ftn.nistagram.campaign.domain.Campaign;
import rs.ac.uns.ftn.nistagram.campaign.domain.OneTimeCampaign;

@AllArgsConstructor
@Component
public class CampaignMapper {

    private final ModelMapper mapper;

    public <T> T map(Object source, Class<T> destinationType) {
        return mapper.map(source, destinationType);
    }

    public Object map(Campaign campaign) {
        if (campaign.getClass().equals(OneTimeCampaign.class)) {
            return mapper.map(campaign, OneTimeCampaignViewDTO.class);
        } else {
            return mapper.map(campaign, LongTermCampaignViewDTO.class);
        }
    }

}
