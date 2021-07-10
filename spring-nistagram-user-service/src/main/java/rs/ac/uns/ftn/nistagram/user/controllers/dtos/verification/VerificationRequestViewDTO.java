package rs.ac.uns.ftn.nistagram.user.controllers.dtos.verification;

import lombok.Data;
import rs.ac.uns.ftn.nistagram.user.controllers.dtos.profile.ProfileViewDTO;
import rs.ac.uns.ftn.nistagram.user.domain.verification.VerificationStatus;

@Data
public class VerificationRequestViewDTO {

    private Long id;
    private ProfileViewDTO profile;
    private CategoryDTO category;
    private String imageUrl;
    private VerificationStatus status;

}
