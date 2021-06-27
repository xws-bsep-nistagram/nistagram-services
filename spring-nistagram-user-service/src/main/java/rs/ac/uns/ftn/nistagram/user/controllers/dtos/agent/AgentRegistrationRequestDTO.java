package rs.ac.uns.ftn.nistagram.user.controllers.dtos.agent;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AgentRegistrationRequestDTO {

    @NotBlank
    private String website;

}
