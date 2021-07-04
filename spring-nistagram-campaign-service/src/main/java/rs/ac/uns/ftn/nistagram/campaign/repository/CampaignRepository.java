package rs.ac.uns.ftn.nistagram.campaign.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.nistagram.campaign.domain.Campaign;

import java.util.List;

@Primary
public interface CampaignRepository<T extends Campaign> extends JpaRepository<T, Long> {

    @Query("select c from Campaign c where c.creator=:username")
    List<T> findByUsername(@Param("username") String username);

}
