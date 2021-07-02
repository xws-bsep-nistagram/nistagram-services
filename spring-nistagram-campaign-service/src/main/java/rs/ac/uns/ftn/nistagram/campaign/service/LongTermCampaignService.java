package rs.ac.uns.ftn.nistagram.campaign.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.campaign.domain.LongTermCampaign;
import rs.ac.uns.ftn.nistagram.campaign.repository.LongTermCampaignRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LongTermCampaignService extends CampaignService<LongTermCampaign> {

    private final LongTermCampaignRepository repository;

    public LongTermCampaignService(LongTermCampaignRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<LongTermCampaign> getDueCampaigns() {
        List<LongTermCampaign> dueCampaigns = getTodayCampaigns().stream()
                .filter(campaign -> !getCampaignDueMoments(campaign).isEmpty())
                .collect(Collectors.toList());
        log.info("Fetched {} campaigns due for showing", dueCampaigns.size());
        return dueCampaigns;
    }

    @Transactional
    public void updateCampaignExposuredMoments(LongTermCampaign campaign) {
        Objects.requireNonNull(campaign);
        List<LocalTime> dueMoments = getCampaignDueMoments(campaign);
        campaign.addMomentsExposured(dueMoments);
        repository.save(campaign);
    }

    private List<LongTermCampaign> getTodayCampaigns() {
        LocalDate today = LocalDate.now();
        return repository.findByDate(today);
    }

    private List<LocalTime> getCampaignDueMoments(LongTermCampaign campaign) {
        LocalTime now = LocalTime.now();
        List<LocalTime> exposureMoments = campaign.getExposureMoments();
        List<LocalTime> momentsExposured = campaign.getMomentsExposured();
        return exposureMoments.stream()
                .filter(time -> time.isBefore(now) && !momentsExposured.contains(time))
                .collect(Collectors.toList());
    }

    @Transactional
    public void resetCampaignExposuredMoments() {
        List<LongTermCampaign> todayCampaigns = getTodayCampaigns();
        todayCampaigns.forEach(LongTermCampaign::clearMomentsExposured);
        repository.saveAll(todayCampaigns);
    }

}
