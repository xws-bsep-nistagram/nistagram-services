package rs.ac.uns.ftn.nistagram.auth.controllers.mappers;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.auth.controllers.dtos.RegistrationRequestDTO;
import rs.ac.uns.ftn.nistagram.auth.domain.RegistrationRequest;

@Component
public class RegistrationRequestMapper {

    public RegistrationRequest toDomain(RegistrationRequestDTO dto) {
        return new RegistrationRequest(
                dto.getUsername(),
                dto.getPassword(),
                dto.getEmail()
        );
    }

}
