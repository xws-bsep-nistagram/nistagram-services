package rs.ac.uns.ftn.nistagram.campaign.controller.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.campaign.controller.dto.CampaignContentDTO;
import rs.ac.uns.ftn.nistagram.campaign.domain.Campaign;
import rs.ac.uns.ftn.nistagram.campaign.domain.OneTimeCampaign;

@AllArgsConstructor
@Component
public class CampaignMapper {

    private final ModelMapper mapper;

    public <T> T map(Object source, Class<T> destinationType) {
        return mapper.map(source, destinationType);
    }

    public CampaignContentDTO map(Campaign campaign) {
        CampaignContentDTO dto = mapper.map(campaign, CampaignContentDTO.class);
        dto.setDurationType(campaign.getClass().equals(OneTimeCampaign.class) ? "ONE_TIME" : "LONG_TERM");
        return dto;
    }

}
