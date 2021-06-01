package rs.ac.uns.ftn.nistagram.user.graph.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserRelationshipRequest {

    @NotBlank(message = "Username is mandatory")
    @NotNull(message = "Username is mandatory")
    private String subject;

    @NotBlank(message = "Username is mandatory")
    @NotNull(message = "Username is mandatory")
    private String target;
}
