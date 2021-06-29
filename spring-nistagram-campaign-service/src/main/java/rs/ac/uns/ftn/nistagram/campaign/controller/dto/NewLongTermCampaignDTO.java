package rs.ac.uns.ftn.nistagram.campaign.controller.dto;

import lombok.Getter;
import rs.ac.uns.ftn.nistagram.campaign.domain.enums.CampaignType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
public class NewLongTermCampaignDTO {

    @NotBlank
    private String name;
    @NotNull
    private CampaignType type;
    @NotNull
    private List<AdvertisementViewDTO> advertisements;
    @NotNull
    private List<LocalTime> exposureMoments;
    @NotNull
    private LocalDate startsOn;
    @NotNull
    private LocalDate endsOn;

}
