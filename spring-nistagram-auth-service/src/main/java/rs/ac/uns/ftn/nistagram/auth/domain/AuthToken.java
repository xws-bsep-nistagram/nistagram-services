package rs.ac.uns.ftn.nistagram.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
/**
 * Represents the fields of a JWT. Additional fields can be added if needed.
 */
public class AuthToken {

    private String username;
    private List<String> permissions;

}
