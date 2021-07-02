package rs.ac.uns.ftn.nistagram.campaign.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.campaign.domain.Campaign;
import rs.ac.uns.ftn.nistagram.campaign.repository.CampaignRepository;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
public abstract class CampaignService<T extends Campaign> {

    private final CampaignRepository<T> repository;

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

    public T create(String username, T campaign) {
        Objects.requireNonNull(campaign);
        campaign.setCreator(username);
        return create(campaign);
    }

}
