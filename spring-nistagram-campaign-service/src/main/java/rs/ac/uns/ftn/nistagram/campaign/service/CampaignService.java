package rs.ac.uns.ftn.nistagram.campaign.service;

import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.campaign.domain.Campaign;
import rs.ac.uns.ftn.nistagram.campaign.exception.CampaignException;
import rs.ac.uns.ftn.nistagram.campaign.exception.EntityNotFoundException;
import rs.ac.uns.ftn.nistagram.campaign.http.client.PostClient;
import rs.ac.uns.ftn.nistagram.campaign.http.payload.MediaLink;
import rs.ac.uns.ftn.nistagram.campaign.http.payload.Post;
import rs.ac.uns.ftn.nistagram.campaign.messaging.event.campaign.CampaignDeleteEvent;
import rs.ac.uns.ftn.nistagram.campaign.messaging.util.TransactionIdHolder;
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
    private final ApplicationEventPublisher publisher;
    private final TransactionIdHolder transactionIdHolder;

    @Transactional
    public T create(T campaign) {
        Objects.requireNonNull(campaign);
        if(campaign.getId() != null && repository.existsById(campaign.getId())) {
            throw new CampaignException(
                    String.format("Campaign with id %d already exists!", campaign.getId()));
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
        return create(campaign);
    }

    private Post convertToPost(T campaign) {
        if (campaign.getAdvertisements() == null || campaign.getAdvertisements().isEmpty()) {
            throw new CampaignException("Campaign advertisements must not be empty!");
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
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Campaign with id %d doesn't exist!", id)));
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
            throw new EntityNotFoundException(String.format("Campaign with id %d doesn't exist!", id));
        }
        Post createdPost = postClient.createAgentPost(campaignUpdate.getCreator(), convertToPost(campaignUpdate));
        postClient.deleteAgentPost(campaignUpdate.getCreator(), campaignUpdate.getContentId());
        campaignUpdate.setContentId(createdPost.getId());
        campaignUpdate.setId(id);
        return repository.save(campaignUpdate);
    }

    @Transactional
    public T update(Long id, T campaignUpdate, String username) {
        T found = get(id);
        if (!found.getCreator().equals(username)) {
            throw new CampaignException("User doesn't own that campaign!");
        }
        campaignUpdate.setCreator(username);
        campaignUpdate.setCreatedOn(found.getCreatedOn());
        campaignUpdate.setContentId(found.getContentId());
        return update(id, campaignUpdate);
    }

    @Transactional
    public void delete(String username, Long id) {
        Campaign found = get(id);
        if (!found.getCreator().equals(username)) {
            throw new CampaignException("User doesn't own that campaign!");
        }
        try {
            postClient.deleteAgentPost(username, found.getContentId());
        } catch(FeignException.NotFound ignored) {}
        publishCampaignDelete(id);
        repository.deleteById(id);
        log.info("Deleted campaign with id {}", id);
    }

    private void publishCampaignDelete(Long id) {
        CampaignDeleteEvent event = new CampaignDeleteEvent(
                transactionIdHolder.getCurrentTransactionId(), id);
        publisher.publishEvent(event);
    }

}
