package rs.ac.uns.ftn.nistagram.campaign.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String creator;
    private String caption;
    private String mediaUrl;
    private String websiteUrl;
    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.REMOVE)
    private List<AdvertisementClick> advertisementClicks;
}
