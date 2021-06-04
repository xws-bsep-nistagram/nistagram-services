package rs.ac.uns.ftn.nistagram.auth.controllers.mappers;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.auth.controllers.dtos.AuthTokenDTO;
import rs.ac.uns.ftn.nistagram.auth.domain.AuthToken;

@Component
public class AuthTokenMapper {

    public AuthTokenDTO toDTO(AuthToken token) {
        AuthTokenDTO dto = new AuthTokenDTO();

        dto.setUsername(token.getUsername());
        dto.setAuthorities(token.getPermissions());

        return dto;
    }

}
