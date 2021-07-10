package rs.ac.uns.ftn.nistagram.campaign.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.nistagram.campaign.domain.LongTermCampaign;

import java.time.LocalDate;
import java.util.List;

public interface LongTermCampaignRepository extends CampaignRepository<LongTermCampaign> {


    @Query("select ltc from LongTermCampaign ltc where ltc.startsOn<=:date and ltc.endsOn>=:date")
    List<LongTermCampaign> findByDate(@Param("date") LocalDate date);

}
