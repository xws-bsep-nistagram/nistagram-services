package rs.ac.uns.ftn.nistagram.campaign.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LongTermCampaign extends Campaign {

    @ElementCollection
    @CollectionTable(name = "exposure_moments", joinColumns = @JoinColumn(name = "campaign_id"))
    @Column(name = "exposure_moment")
    private List<LocalTime> exposureMoments;
    private LocalDateTime createdOn;
    private LocalDate startsOn;
    private LocalDate endsOn;

}
