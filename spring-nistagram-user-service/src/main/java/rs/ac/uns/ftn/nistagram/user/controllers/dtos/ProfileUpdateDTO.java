package rs.ac.uns.ftn.nistagram.user.controllers.dtos;

import lombok.Getter;
import rs.ac.uns.ftn.nistagram.user.domain.user.Gender;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
public class ProfileUpdateDTO {

    @NotBlank
    private String fullName;
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    private String email;
    @NotBlank
    private String phoneNumber;
    @NotNull
    private String dateOfBirth;
    @NotNull
    private Gender gender;
    private String website;
    private String bio;

}
