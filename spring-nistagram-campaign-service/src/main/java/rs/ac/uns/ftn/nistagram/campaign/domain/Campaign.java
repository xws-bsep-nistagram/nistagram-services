package rs.ac.uns.ftn.nistagram.campaign.domain;

import rs.ac.uns.ftn.nistagram.campaign.domain.enums.CampaignType;

import java.time.Instant;
import java.util.List;

public class Campaign {

    private String placedBy;
    private String campaingName;
    private CampaignType campaignType;
    private List<Instant> dailyExposureMoments;
    private int timesExposured;
    private Instant createdAt;
    private Instant startsAt;
    private Instant endsAt;


}
