package rs.ac.uns.ftn.nistagram.campaign.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.nistagram.campaign.controller.dto.AdvertisementViewDTO;
import rs.ac.uns.ftn.nistagram.campaign.controller.dto.NewAdvertisementDTO;
import rs.ac.uns.ftn.nistagram.campaign.domain.Advertisement;
import rs.ac.uns.ftn.nistagram.campaign.service.AdvertisementService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("api/campaigns/advertisements")
public class AdvertisementController {

    private final AdvertisementService service;
    private final ModelMapper mapper;

    @GetMapping
    public List<AdvertisementViewDTO> get(@RequestHeader("username") String username) {
        List<Advertisement> all = service.get(username);
        return all.stream()
                .map(advertisement -> mapper.map(advertisement, AdvertisementViewDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping
    public AdvertisementViewDTO create(@RequestHeader("username") String username,
                                       @Valid @RequestBody NewAdvertisementDTO newAdvertisement) {
        Advertisement created = service.create(username, mapper.map(newAdvertisement, Advertisement.class));
        return mapper.map(created, AdvertisementViewDTO.class);
    }

}
