package rs.ac.uns.ftn.nistagram.campaign.controller.dto;

import lombok.Data;
import rs.ac.uns.ftn.nistagram.campaign.domain.Comment;
import rs.ac.uns.ftn.nistagram.campaign.domain.UserInteraction;
import rs.ac.uns.ftn.nistagram.campaign.domain.enums.CampaignType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
public class LongTermCampaignViewDTO {

    private Long id;
    private String creator;
    private String name;
    private CampaignType type;
    private List<AdvertisementViewDTO> advertisements;
    private List<UserInteraction> userInteractions;
    private List<Comment> comments;
    private List<LocalTime> exposureMoments;
    private LocalDateTime createdOn;
    private LocalDate startsOn;
    private LocalDate endsOn;

}
