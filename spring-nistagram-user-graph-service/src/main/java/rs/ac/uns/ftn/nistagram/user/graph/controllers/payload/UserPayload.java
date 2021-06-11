package rs.ac.uns.ftn.nistagram.user.graph.controllers.payload;

import lombok.Data;
import rs.ac.uns.ftn.nistagram.user.graph.annotations.EnumNamePattern;
import rs.ac.uns.ftn.nistagram.user.graph.domain.ProfileType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserPayload {

    @NotBlank(message = "Username is mandatory")
    @NotNull(message = "Username is mandatory")
    private String username;

    @EnumNamePattern(regexp = "PRIVATE|PUBLIC")
    private ProfileType profileType;
}
