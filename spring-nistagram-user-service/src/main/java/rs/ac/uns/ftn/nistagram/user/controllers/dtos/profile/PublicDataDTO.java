package rs.ac.uns.ftn.nistagram.user.controllers.dtos.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PublicDataDTO {

    private String username;
    private String fullName;
    private String bio;
    private String website;
    private boolean verified;

}
