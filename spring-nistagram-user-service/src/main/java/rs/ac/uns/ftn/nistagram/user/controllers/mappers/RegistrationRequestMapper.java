package rs.ac.uns.ftn.nistagram.user.controllers.mappers;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.user.controllers.dtos.RegistrationRequestDTO;
import rs.ac.uns.ftn.nistagram.user.domain.user.PersonalData;
import rs.ac.uns.ftn.nistagram.user.domain.user.RegistrationRequest;

@Component
public class RegistrationRequestMapper {

    public RegistrationRequest toDomain(RegistrationRequestDTO dto) {
        PersonalData personalData = null;
        if (dto.getPersonalData() != null) {
            personalData = new PersonalData(
                    dto.getPersonalData().getFullName(),
                    dto.getEmail(),
                    dto.getPersonalData().getPhoneNumber(),
                    dto.getPersonalData().getGender(),
                    dto.getPersonalData().getDateOfBirth(),
                    dto.getPersonalData().getWebsite(),
                    dto.getPersonalData().getBio()
            );
        }
        return new RegistrationRequest(
                dto.getUsername(),
                dto.getPassword(),
                dto.getEmail(),
                personalData
        );
    }

}
