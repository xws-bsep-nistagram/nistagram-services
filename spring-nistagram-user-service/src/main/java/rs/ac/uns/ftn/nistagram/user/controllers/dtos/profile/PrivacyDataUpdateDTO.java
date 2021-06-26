package rs.ac.uns.ftn.nistagram.user.controllers.dtos.profile;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class PrivacyDataUpdateDTO {

    @NotNull
    private boolean profilePrivate;
    @NotNull
    private boolean messagesFromNonFollowersAllowed;
    @NotNull
    private boolean taggable;

}
