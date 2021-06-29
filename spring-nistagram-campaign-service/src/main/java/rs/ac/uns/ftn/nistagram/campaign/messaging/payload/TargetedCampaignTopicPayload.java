package rs.ac.uns.ftn.nistagram.campaign.messaging.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import rs.ac.uns.ftn.nistagram.campaign.domain.enums.CampaignType;

import java.util.List;

@AllArgsConstructor
@Getter
public class TargetedCampaignTopicPayload {

    private Long campaignId;
    private List<String> users;
    private CampaignType type;
}
