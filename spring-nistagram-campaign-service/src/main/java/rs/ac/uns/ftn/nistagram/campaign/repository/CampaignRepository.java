package rs.ac.uns.ftn.nistagram.campaign.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.nistagram.campaign.domain.Campaign;

@Primary
public interface CampaignRepository<T extends Campaign> extends JpaRepository<T, Long> {
}
