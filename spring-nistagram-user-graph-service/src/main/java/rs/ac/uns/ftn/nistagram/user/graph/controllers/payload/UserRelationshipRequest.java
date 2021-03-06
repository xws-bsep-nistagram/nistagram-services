package rs.ac.uns.ftn.nistagram.user.graph.controllers.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UserRelationshipRequest {

    @NotBlank(message = "Username is mandatory")
    @NotNull(message = "Username is mandatory")
    private String subject;

    @NotBlank(message = "Username is mandatory")
    @NotNull(message = "Username is mandatory")
    private String target;

    public UserRelationshipRequest(String subject, String target){
        this.subject = subject;
        this.target = target;
    }
}
