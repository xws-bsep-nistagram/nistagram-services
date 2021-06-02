package rs.ac.uns.ftn.nistagram.auth.controllers.dtos;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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

}
