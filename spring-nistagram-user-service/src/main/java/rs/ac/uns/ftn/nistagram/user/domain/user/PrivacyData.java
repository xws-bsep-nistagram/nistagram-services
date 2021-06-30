package rs.ac.uns.ftn.nistagram.user.domain.user;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
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
