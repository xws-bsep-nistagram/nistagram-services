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
        Post createdPost = postClient.createAgentPost(username, convertToPost(campaign));
        campaign.setContentId(createdPost.getId());
        T created = create(campaign);
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

    @Transactional(readOnly = true)
    public List<T> get(String username) {
        List<T> all = repository.findByUsername(username);
        log.info("Fetching {} stories for agent '{}'", all.size(), username);
        return all;
    }

    @Transactional
    public T update(Long id, T campaignUpdate) {
        if (!repository.existsById(id)) {
            throw new RuntimeException();
        }
        campaignUpdate.setId(id);
        return repository.save(campaignUpdate);
    }

    @Transactional
    public T update(Long id, T campaignUpdate, String username) {
        T found = get(id);
        if (!found.getCreator().equals(username)) {
            throw new RuntimeException("User doesn't own that campaign!");
        }
        campaignUpdate.setCreator(username);
        campaignUpdate.setCreatedOn(found.getCreatedOn());
        return update(id, campaignUpdate);
    }

}
