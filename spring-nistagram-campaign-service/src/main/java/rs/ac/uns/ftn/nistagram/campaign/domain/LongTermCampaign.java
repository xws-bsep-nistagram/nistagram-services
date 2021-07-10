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
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LongTermCampaign extends Campaign {

    @NotNull
    @ElementCollection
    @CollectionTable(name = "exposure_moments", joinColumns = @JoinColumn(name = "campaign_id"))
    @Column(name = "exposure_moment")
    private List<LocalTime> exposureMoments;
    @ElementCollection
    @CollectionTable(name = "moments_exposured", joinColumns = @JoinColumn(name = "campaign_id"))
    @Column(name = "moments_exposured")
    private List<LocalTime> momentsExposured;
    @NotNull
    private LocalDate startsOn;
    @NotNull
    private LocalDate endsOn;

    public void addMomentsExposured(List<LocalTime> momentsExposured) {
        if (this.momentsExposured == null) {
            this.momentsExposured = new ArrayList<>();
        }
        if (momentsExposured != null) {
            this.momentsExposured.addAll(momentsExposured);
        }
    }

    public void clearMomentsExposured() {
        this.momentsExposured = new ArrayList<>();
    }
}
