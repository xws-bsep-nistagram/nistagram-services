package rs.ac.uns.ftn.nistagram.campaign.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.campaign.domain.enums.CampaignType;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotBlank
    private String creator;
    @NotBlank
    private String name;
    @NotNull
    private CampaignType type;
    @NotNull
    private LocalDateTime createdOn;
    @OneToMany
    private List<Advertisement> advertisements;
    @OneToMany(mappedBy = "campaign")
    private List<UserInteraction> userInteractions;
    @OneToMany(mappedBy = "campaign")
    private List<Comment> comments;
    @Embedded
    private TargetedGroup targetedGroup;
}
