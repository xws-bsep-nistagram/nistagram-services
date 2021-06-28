package rs.ac.uns.ftn.nistagram.user.controllers.dtos.agent;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserPromotionRequest {
    @NotEmpty
    @NotNull
    private String username;
    @NotEmpty
    @NotNull
    private String website;
}
