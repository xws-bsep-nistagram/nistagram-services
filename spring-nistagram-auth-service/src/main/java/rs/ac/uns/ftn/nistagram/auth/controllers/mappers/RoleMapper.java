package rs.ac.uns.ftn.nistagram.auth.controllers.mappers;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.auth.controllers.dtos.RolesDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleMapper {

    public RolesDTO map(UserDetails user) {
        List<String> roles = user.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList());
        return new RolesDTO(roles);
    }

}
