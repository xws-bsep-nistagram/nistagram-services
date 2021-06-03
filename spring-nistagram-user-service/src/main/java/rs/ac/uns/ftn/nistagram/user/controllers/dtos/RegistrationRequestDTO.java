package rs.ac.uns.ftn.nistagram.user.controllers.dtos;

import lombok.Getter;

@Getter
public class RegistrationRequestDTO {

    private String username;
    private String password;
    private String email;
    private PersonalDataDTO personalData;

}
