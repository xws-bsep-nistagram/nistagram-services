package rs.ac.uns.ftn.nistagram.campaign.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.nistagram.campaign.domain.OneTimeCampaign;

import java.time.LocalDateTime;
import java.util.List;

public interface OneTimeCampaignRepository extends CampaignRepository<OneTimeCampaign> {

    @Query("select otc from OneTimeCampaign otc where otc.exposureMoment <= :time and otc.exposured=false")
    List<OneTimeCampaign> findNonExposured(@Param("time") LocalDateTime time);

}
