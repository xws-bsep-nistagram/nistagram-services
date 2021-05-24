package rs.ac.uns.ftn.nistagram.campaign.domain;

import rs.ac.uns.ftn.nistagram.campaign.domain.enums.CampaignType;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Campaign {
    private String placedBy;
    private String campaingName;
    private CampaignType campaignType;
    private List<LocalTime> dailyExposureMoments;
    private int timesExposured;
    private LocalDateTime createdAt;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
}
