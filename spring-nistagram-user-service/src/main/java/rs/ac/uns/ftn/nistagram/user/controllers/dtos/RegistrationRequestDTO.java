package rs.ac.uns.ftn.nistagram.user.controllers.dtos;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import rs.ac.uns.ftn.nistagram.user.domain.user.Gender;

import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
public class RegistrationRequestDTO {

    @NotBlank
    @Length(min = 8)
    private String username;
    @NotBlank
    @Length(min = 8)
    private String password;
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    private String email;
    @NotBlank
    private String fullName;
    @NotBlank
    private String phoneNumber;
    @NotNull
    private Gender gender;
    @NotNull
    private LocalDate dateOfBirth;
    private String website;
    private String bio;

}
