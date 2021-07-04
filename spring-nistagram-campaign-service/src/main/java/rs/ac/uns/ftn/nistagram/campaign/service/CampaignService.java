package rs.ac.uns.ftn.nistagram.campaign.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.campaign.domain.Campaign;
import rs.ac.uns.ftn.nistagram.campaign.http.PostClient;
import rs.ac.uns.ftn.nistagram.campaign.http.payload.MediaLink;
import rs.ac.uns.ftn.nistagram.campaign.http.payload.Post;
import rs.ac.uns.ftn.nistagram.campaign.repository.CampaignRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class CampaignService<T extends Campaign> {

    private final CampaignRepository<T> repository;
    private final PostClient postClient;

    @Transactional
    public T create(T campaign) {
        Objects.requireNonNull(campaign);
        if(campaign.getId() != null && repository.existsById(campaign.getId())) {
            throw new RuntimeException();
        }
        campaign.setCreatedOn(LocalDateTime.now());
        T created = repository.save(campaign);
        log.info("Agent '{}' created campaign with id {}", campaign.getCreator(), campaign.getId());
        return created;
    }

    @Transactional
    public T create(String username, T campaign) {
        Objects.requireNonNull(campaign);
        campaign.setCreator(username);
        T created = create(campaign);
        postClient.createAgentPost(username, convertToPost(campaign));
        return created;
    }

    private Post convertToPost(T campaign) {
        if (campaign.getAdvertisements() == null || campaign.getAdvertisements().isEmpty()) {
            throw new RuntimeException("Campaign advertisements must not be empty!");
        }
        List<MediaLink> links = campaign.getAdvertisements()
                .stream()
                .map(ad -> new MediaLink(ad.getMediaUrl()))
                .collect(Collectors.toList());
        return new Post(campaign.getCreator(), campaign.getName(), links);
    }

    @Transactional(readOnly = true)
    public T get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException());
    }

}
