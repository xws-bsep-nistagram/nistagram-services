package rs.ac.uns.ftn.nistagram.feed.messaging.event.campaign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import rs.ac.uns.ftn.nistagram.feed.messaging.payload.campaign.CampaignPayload;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CampaignsPublishEvent {

    private String transactionId;
    private List<CampaignPayload> payload;

}
