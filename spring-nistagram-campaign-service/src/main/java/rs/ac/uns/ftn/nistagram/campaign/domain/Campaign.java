package rs.ac.uns.ftn.nistagram.campaign.domain;

import rs.ac.uns.ftn.nistagram.campaign.domain.enums.CampaignType;

import java.time.Instant;
import java.util.List;

public class Campaign {

    private String placedBy;
    private String name;
    private CampaignType type;
    private List<Instant> dailyExposureMoments;
    private int timesShown;
    private Instant createdAt;
    private Instant startsAt;
    private Instant endsAt;

}
