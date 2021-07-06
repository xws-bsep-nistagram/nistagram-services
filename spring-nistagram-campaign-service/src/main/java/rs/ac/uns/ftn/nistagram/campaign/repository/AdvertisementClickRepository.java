package rs.ac.uns.ftn.nistagram.campaign.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.nistagram.campaign.domain.AdvertisementClick;

public interface AdvertisementClickRepository extends JpaRepository<AdvertisementClick, Long> {

    @Query("select count(distinct ac.username) from AdvertisementClick ac where ac.advertisement.id=:advertisementId")
    Long countUniqueUserAdvertisementClicks(@Param("advertisementId") Long advertisementId);

    Long countByAdvertisementId(Long id);

}
