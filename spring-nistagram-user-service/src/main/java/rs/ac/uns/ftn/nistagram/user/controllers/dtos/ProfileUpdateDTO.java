package rs.ac.uns.ftn.nistagram.user.controllers.dtos;

import lombok.Getter;
import rs.ac.uns.ftn.nistagram.user.domain.user.Gender;

@Getter
public class ProfileUpdateDTO {

    private String fullName;
    private String email;
    private String phoneNumber;
    private String dateOfBirth;
    private Gender gender;
    private String website;
    private String bio;

}
