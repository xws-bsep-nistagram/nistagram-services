package rs.ac.uns.ftn.nistagram.campaign.controller.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import rs.ac.uns.ftn.nistagram.campaign.domain.TargetedGroup;
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
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime exposureMoment;
    @NotNull
    private TargetedGroup targetedGroup;
}
