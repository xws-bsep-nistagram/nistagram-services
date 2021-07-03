package rs.ac.uns.ftn.nistagram.campaign.controller.dto;

import lombok.Data;
import rs.ac.uns.ftn.nistagram.campaign.domain.Comment;
import rs.ac.uns.ftn.nistagram.campaign.domain.UserInteraction;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CampaignContentDTO {

    private Long id;
    private String name;
    private List<AdvertisementViewDTO> advertisements;
    private List<UserInteraction> userInteractions;
    private List<Comment> comments;
    private LocalDateTime createdOn;

}
