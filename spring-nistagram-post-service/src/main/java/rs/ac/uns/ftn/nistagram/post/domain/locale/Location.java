package rs.ac.uns.ftn.nistagram.post.domain.locale;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Embeddable
public class Location {
    private String address;
    private float latitude;
    private float longitude;
}
