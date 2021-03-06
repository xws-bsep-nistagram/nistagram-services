package rs.ac.uns.ftn.nistagram.user.controllers.dtos.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import rs.ac.uns.ftn.nistagram.user.domain.user.Gender;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ProfileViewDTO {

    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String website;
    private String bio;
    private boolean verified;

}
