package rs.ac.uns.ftn.nistagram.user.domain.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class PrivacyData {

    private boolean profilePrivate;
    private boolean messagesFromNonFollowersAllowed;
    private boolean taggable;

    public PrivacyData() {
        this.profilePrivate = true;
        this.messagesFromNonFollowersAllowed = false;
        this.taggable = true;
    }

}
