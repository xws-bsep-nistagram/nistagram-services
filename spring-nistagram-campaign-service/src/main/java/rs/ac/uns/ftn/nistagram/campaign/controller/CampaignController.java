package rs.ac.uns.ftn.nistagram.campaign.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.nistagram.campaign.controller.dto.CampaignContentDTO;
import rs.ac.uns.ftn.nistagram.campaign.controller.dto.LongTermCampaignViewDTO;
import rs.ac.uns.ftn.nistagram.campaign.controller.dto.NewLongTermCampaignDTO;
import rs.ac.uns.ftn.nistagram.campaign.controller.dto.NewOneTimeCampaignDTO;
import rs.ac.uns.ftn.nistagram.campaign.controller.dto.OneTimeCampaignViewDTO;
import rs.ac.uns.ftn.nistagram.campaign.domain.Campaign;
import rs.ac.uns.ftn.nistagram.campaign.domain.LongTermCampaign;
import rs.ac.uns.ftn.nistagram.campaign.domain.OneTimeCampaign;
import rs.ac.uns.ftn.nistagram.campaign.service.CampaignService;
import rs.ac.uns.ftn.nistagram.campaign.service.LongTermCampaignService;
import rs.ac.uns.ftn.nistagram.campaign.service.OneTimeCampaignService;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("api/campaigns")
public class CampaignController {

    private final OneTimeCampaignService oneTimeCampaignService;
    private final LongTermCampaignService longTermCampaignService;
    private final CampaignService<Campaign> campaignService;
    private final ModelMapper mapper;

    @PostMapping("one-time")
    public OneTimeCampaignViewDTO create(@RequestHeader("username") String username,
                                         @Valid @RequestBody NewOneTimeCampaignDTO newCampaign) {
        OneTimeCampaign created = oneTimeCampaignService.create(username, mapper.map(newCampaign, OneTimeCampaign.class));
        return mapper.map(created, OneTimeCampaignViewDTO.class);
    }

    @PostMapping("long-term")
    public LongTermCampaignViewDTO create(@RequestHeader("username") String username,
                                           @Valid @RequestBody NewLongTermCampaignDTO newCampaign) {
        LongTermCampaign created = longTermCampaignService.create(username, mapper.map(newCampaign, LongTermCampaign.class));
        return mapper.map(created, LongTermCampaignViewDTO.class);
    }

    @GetMapping("{id}")
    public CampaignContentDTO get(@PathVariable Long id) {
        Campaign found = campaignService.get(id);
        return mapper.map(found, CampaignContentDTO.class);
    }

}
