package rs.ac.uns.ftn.nistagram.campaign.controller.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AdvertisementViewDTO {

    private Long id;
    private String creator;
    private String caption;
    private String mediaUrl;
    private String websiteUrl;
    private long redirectCount;

}
