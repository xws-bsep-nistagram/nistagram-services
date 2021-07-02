package rs.ac.uns.ftn.nistagram.campaign.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.nistagram.campaign.domain.Campaign;

public interface CampaignRepository<T extends Campaign> extends JpaRepository<T, Long> {
}
