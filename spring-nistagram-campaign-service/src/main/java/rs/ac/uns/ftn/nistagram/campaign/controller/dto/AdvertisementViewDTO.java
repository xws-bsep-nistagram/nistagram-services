package rs.ac.uns.ftn.nistagram.campaign.controller.dto;

import lombok.Data;

@Data
public class AdvertisementViewDTO {

    private Long id;
    private String creator;
    private String caption;
    private String mediaUrl;
    private String websiteUrl;
    private long redirectCount;

}
