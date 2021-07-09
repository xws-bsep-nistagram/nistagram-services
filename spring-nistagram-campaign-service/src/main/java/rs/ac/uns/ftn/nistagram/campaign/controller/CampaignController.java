package rs.ac.uns.ftn.nistagram.campaign.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.campaign.controller.dto.CampaignContentDTO;
import rs.ac.uns.ftn.nistagram.campaign.controller.dto.LongTermCampaignViewDTO;
import rs.ac.uns.ftn.nistagram.campaign.controller.dto.NewLongTermCampaignDTO;
import rs.ac.uns.ftn.nistagram.campaign.controller.dto.NewOneTimeCampaignDTO;
import rs.ac.uns.ftn.nistagram.campaign.controller.dto.OneTimeCampaignViewDTO;
import rs.ac.uns.ftn.nistagram.campaign.controller.mapper.CampaignMapper;
import rs.ac.uns.ftn.nistagram.campaign.domain.Campaign;
import rs.ac.uns.ftn.nistagram.campaign.domain.LongTermCampaign;
import rs.ac.uns.ftn.nistagram.campaign.domain.OneTimeCampaign;
import rs.ac.uns.ftn.nistagram.campaign.service.CampaignService;
import rs.ac.uns.ftn.nistagram.campaign.service.LongTermCampaignService;
import rs.ac.uns.ftn.nistagram.campaign.service.OneTimeCampaignService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("api/campaigns")
public class CampaignController {

    private final OneTimeCampaignService oneTimeCampaignService;
    private final LongTermCampaignService longTermCampaignService;
    private final CampaignService<Campaign> campaignService;
    private final CampaignMapper mapper;

    @PostMapping("one-time")
    public OneTimeCampaignViewDTO create(
            @RequestHeader(value = "${nistagram.headers.user}", required = false) String username,
            @RequestHeader(value = "${nistagram.headers.agent}", required = false) String agent,
            @Valid @RequestBody NewOneTimeCampaignDTO newCampaign) {

        String caller = username == null ? agent : username;
        OneTimeCampaign created = oneTimeCampaignService.create(
                caller, mapper.map(newCampaign, OneTimeCampaign.class));

        return mapper.map(created, OneTimeCampaignViewDTO.class);
    }

    @PostMapping("long-term")
    public LongTermCampaignViewDTO create(
            @RequestHeader(value = "${nistagram.headers.user}", required = false) String username,
            @RequestHeader(value = "${nistagram.headers.agent}", required = false) String agent,
            @Valid @RequestBody NewLongTermCampaignDTO newCampaign) {

        String caller = username == null ? agent : username;
        LongTermCampaign created = longTermCampaignService.create(
                caller, mapper.map(newCampaign, LongTermCampaign.class));

        return mapper.map(created, LongTermCampaignViewDTO.class);
    }

    @PutMapping("one-time/{id}")
    public OneTimeCampaignViewDTO update(
            @RequestHeader("${nistagram.headers.user}") String username,
            @Valid @RequestBody NewOneTimeCampaignDTO campaignUpdate,
            @PathVariable Long id) {
        OneTimeCampaign updated = oneTimeCampaignService.update(
                id, mapper.map(campaignUpdate, OneTimeCampaign.class), username);
        return mapper.map(updated, OneTimeCampaignViewDTO.class);
    }

    @PutMapping("long-term/{id}")
    public LongTermCampaignViewDTO update(
            @RequestHeader("${nistagram.headers.user}") String username,
            @Valid @RequestBody NewLongTermCampaignDTO campaignUpdate,
            @PathVariable Long id) {
        LongTermCampaign updated = longTermCampaignService.update(
                id, mapper.map(campaignUpdate, LongTermCampaign.class), username);
        return mapper.map(updated, LongTermCampaignViewDTO.class);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Campaign found = campaignService.get(id);
        return ResponseEntity.ok(mapper.map(found));
    }

    @DeleteMapping("{id}")
    public void delete(@RequestHeader("username") String username, @PathVariable Long id) {
        campaignService.delete(username, id);
    }

    @GetMapping
    public List<CampaignContentDTO> getAll(
            @RequestHeader(value = "${nistagram.headers.user}", required = false) String username,
            @RequestHeader(value = "${nistagram.headers.agent}", required = false) String agent) {

        String caller = username == null ? agent : username;
        List<Campaign> all = campaignService.get(caller);
        return all.stream()
                .map(campaign -> mapper.map(campaign, CampaignContentDTO.class))
                .collect(Collectors.toList());
    }

    // TODO [TEST]
    @GetMapping("external")
    public List<CampaignContentDTO> getAllViaExternal(
            @RequestHeader(value = "${nistagram.headers.user}", required = false) String username,
            @RequestHeader(value = "${nistagram.headers.agent}", required = false) String agent) {

        List<Campaign> all = campaignService.get(username);
        return all.stream()
                .map(campaign -> mapper.map(campaign, CampaignContentDTO.class))
                .collect(Collectors.toList());
    }

}
