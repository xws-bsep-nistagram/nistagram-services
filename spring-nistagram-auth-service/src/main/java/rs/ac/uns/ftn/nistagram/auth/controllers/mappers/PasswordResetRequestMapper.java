package rs.ac.uns.ftn.nistagram.auth.controllers.mappers;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.auth.controllers.dtos.PasswordResetRequestDTO;
import rs.ac.uns.ftn.nistagram.auth.domain.PasswordResetRequest;

@Service
public class PasswordResetRequestMapper {

    public PasswordResetRequest map(PasswordResetRequestDTO dto) {
        return new PasswordResetRequest(dto.getEmail());
    }

}
