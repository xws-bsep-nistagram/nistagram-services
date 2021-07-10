package rs.ac.uns.ftn.nistagram.campaign.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdvertisementStatsDTO {

    private Long id;
    private Long clicks;
    private Long uniqueClicks;

}
