package rs.ac.uns.ftn.nistagram.campaign.controller.dto;

import lombok.Data;
import rs.ac.uns.ftn.nistagram.campaign.domain.Comment;
import rs.ac.uns.ftn.nistagram.campaign.domain.TargetedGroup;
import rs.ac.uns.ftn.nistagram.campaign.domain.UserInteraction;
import rs.ac.uns.ftn.nistagram.campaign.domain.enums.CampaignType;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OneTimeCampaignViewDTO {

    private Long id;
    private String creator;
    private String name;
    private CampaignType type;
    private List<AdvertisementViewDTO> advertisements;
    private List<UserInteraction> userInteractions;
    private List<Comment> comments;
    private LocalDateTime exposureMoment;
    private TargetedGroup targetedGroup;

}
