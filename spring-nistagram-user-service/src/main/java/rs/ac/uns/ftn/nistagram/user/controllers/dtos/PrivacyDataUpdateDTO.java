package rs.ac.uns.ftn.nistagram.user.controllers.dtos;

import lombok.Getter;

@Getter
public class PrivacyDataUpdateDTO {

    private boolean profilePrivate;
    private boolean messagesFromNonFollowersAllowed;
    private boolean taggable;

}
