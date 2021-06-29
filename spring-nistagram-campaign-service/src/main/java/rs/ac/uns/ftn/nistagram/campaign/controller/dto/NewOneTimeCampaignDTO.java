package rs.ac.uns.ftn.nistagram.campaign.controller.dto;

import lombok.Getter;
import rs.ac.uns.ftn.nistagram.campaign.domain.enums.CampaignType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class NewOneTimeCampaignDTO {

    @NotBlank
    private String name;
    @NotNull
    private CampaignType type;
    @NotNull
    private List<AdvertisementViewDTO> advertisements;
    @NotNull
    private LocalDateTime exposureMoment;

}
