package rs.ac.uns.ftn.nistagram.user.graph.controllers.payload;

import lombok.AllArgsConstructor;
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

    public UserRelationshipRequest(String subject, String target){
        this.subject = subject;
        this.target = target;
    }
}
