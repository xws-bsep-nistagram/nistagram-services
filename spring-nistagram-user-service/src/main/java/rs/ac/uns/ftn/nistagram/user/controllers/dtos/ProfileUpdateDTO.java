package rs.ac.uns.ftn.nistagram.user.controllers.dtos;

import lombok.Getter;
import rs.ac.uns.ftn.nistagram.user.controllers.constraints.EmailConstraint;
import rs.ac.uns.ftn.nistagram.user.controllers.constraints.EnumPattern;
import rs.ac.uns.ftn.nistagram.user.controllers.constraints.FullnameConstraint;
import rs.ac.uns.ftn.nistagram.user.domain.user.Gender;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
public class ProfileUpdateDTO {

    @FullnameConstraint
    private String fullName;
    @EmailConstraint
    private String email;
    @NotBlank
    private String phoneNumber;
    @NotNull
    private String dateOfBirth;
    @EnumPattern(regexp = "MALE|FEMALE")
    private Gender gender;
    private String website;
    private String bio;

}
