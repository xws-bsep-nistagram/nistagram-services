package rs.ac.uns.ftn.nistagram.api.gateway.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthoritiesRequest {

    private final String jwt;

}
