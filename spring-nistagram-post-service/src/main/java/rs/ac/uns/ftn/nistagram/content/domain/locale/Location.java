package rs.ac.uns.ftn.nistagram.content.domain.locale;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Embeddable
@Getter
@Setter
public class Location {
    @NotEmpty
    private String address;
    private Float latitude;
    private Float longitude;
}
