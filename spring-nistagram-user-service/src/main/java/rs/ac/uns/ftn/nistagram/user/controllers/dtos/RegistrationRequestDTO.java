package rs.ac.uns.ftn.nistagram.user.controllers.dtos;

import lombok.Getter;
import rs.ac.uns.ftn.nistagram.user.controllers.constraints.*;
import rs.ac.uns.ftn.nistagram.user.domain.user.Gender;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
public class RegistrationRequestDTO {

    @UsernameConstraint
    private String username;
    @PasswordConstraint
    private String password;
    @EmailConstraint
    private String email;
    @FullnameConstraint
    private String fullName;
    @NotBlank
    private String phoneNumber;
    @EnumPattern(regexp = "MALE|FEMALE|OTHER")
    private Gender gender;
    @NotNull
    private LocalDate dateOfBirth;
    private String website;
    private String bio;

}
