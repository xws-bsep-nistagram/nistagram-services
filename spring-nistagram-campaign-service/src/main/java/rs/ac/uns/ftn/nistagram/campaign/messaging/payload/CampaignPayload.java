package rs.ac.uns.ftn.nistagram.campaign.messaging.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.campaign.domain.enums.CampaignType;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CampaignPayload {

    private Long campaignId;
    private List<String> users;
    private CampaignType type;
}
