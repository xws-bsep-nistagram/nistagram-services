package rs.ac.uns.ftn.nistagram.user.domain.verification;

import rs.ac.uns.ftn.nistagram.user.domain.user.User;

public class VerificationRequest {

    private User requiredBy;
    private String fullName;
    private String verificationDocumentUrl;
    private String profileType;
    private VerificationStatus verificationStatus;

}
