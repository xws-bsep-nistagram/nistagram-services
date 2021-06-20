package rs.ac.uns.ftn.nistagram.user.domain.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import rs.ac.uns.ftn.nistagram.user.domain.user.PersonalData;

@AllArgsConstructor
@Getter
public class RegistrationRequest {

    private String username;
    private String password;
    private String email;
    private PersonalData personalData;

}
