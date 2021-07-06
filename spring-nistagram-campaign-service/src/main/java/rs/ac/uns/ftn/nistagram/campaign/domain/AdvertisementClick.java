package rs.ac.uns.ftn.nistagram.campaign.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class AdvertisementClick {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String username;
    @ManyToOne
    public Advertisement advertisement;

    protected AdvertisementClick() {}

    public AdvertisementClick(String username, Advertisement advertisement) {
        this.username = username;
        this.advertisement = advertisement;
    }

}
