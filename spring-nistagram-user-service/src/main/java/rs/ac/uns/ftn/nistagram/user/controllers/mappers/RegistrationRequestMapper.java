package rs.ac.uns.ftn.nistagram.user.controllers.mappers;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.user.controllers.dtos.RegistrationRequestDTO;
import rs.ac.uns.ftn.nistagram.user.domain.user.PersonalData;
import rs.ac.uns.ftn.nistagram.user.domain.user.request.RegistrationRequest;

@Component
public class RegistrationRequestMapper {

    public RegistrationRequest toDomain(RegistrationRequestDTO dto) {
        PersonalData personalData = new PersonalData(
                dto.getFullName(),
                dto.getEmail(),
                dto.getPhoneNumber(),
                dto.getGender(),
                dto.getDateOfBirth(),
                dto.getWebsite(),
                dto.getBio()
        );
        return new RegistrationRequest(
                dto.getUsername(),
                dto.getPassword(),
                dto.getEmail(),
                personalData
        );
    }

}
