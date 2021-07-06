package rs.ac.uns.ftn.nistagram.campaign.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.campaign.controller.dto.AdvertisementStatsDTO;
import rs.ac.uns.ftn.nistagram.campaign.service.AdvertisementClickService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/campaigns/clicks")
public class AdvertisementClickController {

    private final AdvertisementClickService service;

    @GetMapping("{advertisementId}")
    public AdvertisementStatsDTO getStats(@PathVariable Long advertisementId) {
        Long clicks = service.countClicks(advertisementId);
        Long uniqueClicks = service.countUniqueUserClicks(advertisementId);
        return new AdvertisementStatsDTO(advertisementId, clicks, uniqueClicks);
    }

    @PostMapping("{advertisementId}")
    public void registerClick(@RequestHeader("username") String username, @PathVariable Long advertisementId) {
        service.registerClick(username, advertisementId);
    }

}
