package rs.ac.uns.ftn.nistagram.feed.messaging.event.campaign;

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
