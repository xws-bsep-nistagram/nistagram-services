package rs.ac.uns.ftn.nistagram.campaign.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.campaign.domain.Advertisement;
import rs.ac.uns.ftn.nistagram.campaign.repository.AdvertisementRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
@Service
public class AdvertisementService {

    private final AdvertisementRepository repository;

    public Advertisement create(String username, Advertisement advertisement) {
        Objects.requireNonNull(advertisement);
        Objects.requireNonNull(username);
        advertisement.setCreator(username);
        return create(advertisement);
    }

    @Transactional
    public Advertisement create(Advertisement advertisement) {
        Objects.requireNonNull(advertisement);
        Objects.requireNonNull(advertisement.getCreator());
        if (advertisement.getId() != null && repository.existsById(advertisement.getId())) {
            throw new RuntimeException();
        }
        advertisement.setCreatedOn(LocalDateTime.now());
        Advertisement created = repository.save(advertisement);
        log.info("User '{}' created advertisement with id {}", created.getCreator(), created.getId());
        return created;
    }

    @Transactional(readOnly = true)
    public List<Advertisement> get(String username) {
        Objects.requireNonNull(username);
        List<Advertisement> all = repository.findByCreator(username);
        log.info("Fetched all advertisements for user {}", username);
        return all;
    }
}
