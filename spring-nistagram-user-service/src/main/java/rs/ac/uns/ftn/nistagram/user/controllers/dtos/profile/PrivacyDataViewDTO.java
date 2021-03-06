package rs.ac.uns.ftn.nistagram.user.controllers.dtos.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PrivacyDataViewDTO {

    private boolean profilePrivate;
    private boolean messagesFromNonFollowersAllowed;
    private boolean taggable;

}
