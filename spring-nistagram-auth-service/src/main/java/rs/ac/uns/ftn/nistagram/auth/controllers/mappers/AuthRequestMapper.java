package rs.ac.uns.ftn.nistagram.auth.controllers.mappers;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.auth.controllers.dtos.AuthRequestDTO;
import rs.ac.uns.ftn.nistagram.auth.domain.AuthRequest;

@Component
public class AuthRequestMapper {

    public AuthRequest toDomain(AuthRequestDTO dto) {
        return new AuthRequest(dto.getUsername(), dto.getPassword());
    }

}
