package rs.ac.uns.ftn.nistagram.campaign.messaging.event.campaign;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CampaignDeleteEvent {

    private String transactionId;
    private Long campaignId;

}
