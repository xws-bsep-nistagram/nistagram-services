package rs.ac.uns.ftn.nistagram.user.domain.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class AdministrativeData {

    private boolean verified;
    private boolean banned;

    public AdministrativeData() {
        this.verified = false;
        this.banned = false;
    }

}
