package rs.ac.uns.ftn.nistagram.auth.controllers.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthTokenDTO {

    private String username;
    private List<String> authorities;

}
