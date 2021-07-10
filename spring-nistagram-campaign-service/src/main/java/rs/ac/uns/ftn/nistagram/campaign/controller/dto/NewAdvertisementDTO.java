package rs.ac.uns.ftn.nistagram.campaign.controller.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class NewAdvertisementDTO {

    private String caption;
    @NotBlank
    private String mediaUrl;
    @NotBlank
    private String websiteUrl;

}
