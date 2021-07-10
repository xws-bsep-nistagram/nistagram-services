package rs.ac.uns.ftn.nistagram.campaign.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.campaign.domain.Advertisement;
import rs.ac.uns.ftn.nistagram.campaign.domain.AdvertisementClick;
import rs.ac.uns.ftn.nistagram.campaign.repository.AdvertisementClickRepository;

@Slf4j
@AllArgsConstructor
@Service
public class AdvertisementClickService {

    private final AdvertisementClickRepository repository;
    private final AdvertisementService service;

    @Transactional
    public AdvertisementClick registerClick(String username, Long id) {
        Advertisement found = service.get(id);
        AdvertisementClick newClick = new AdvertisementClick(username, found);
        AdvertisementClick created = repository.save(newClick);
        log.info("Registered click by user '{}' on advertisement with id {}", username, id);
        return created;
    }

    @Transactional(readOnly = true)
    public Long countUniqueUserClicks(Long advertisementId) {
        Long clickCount = repository.countUniqueUserAdvertisementClicks(advertisementId);
        log.info("Got {} unique user clicks for advertisement with id {}", clickCount, advertisementId);
        return clickCount;
    }

    @Transactional(readOnly = true)
    public Long countClicks(Long advertisementId) {
        Long clickCount = repository.countByAdvertisementId(advertisementId);
        log.info("Got {} clicks for advertisement with id {}", clickCount, advertisementId);
        return clickCount;
    }

}
