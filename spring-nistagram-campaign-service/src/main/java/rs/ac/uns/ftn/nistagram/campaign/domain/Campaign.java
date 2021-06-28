package rs.ac.uns.ftn.nistagram.campaign.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.campaign.domain.enums.CampaignType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String creator;
    private String name;
    private CampaignType type;
    private Long timesExposured;
    @OneToMany
    private List<Advertisement> advertisements;
    @OneToMany(mappedBy = "campaign")
    private List<UserInteraction> userInteractions;
    @OneToMany(mappedBy = "campaign")
    private List<Comment> comments;

}
