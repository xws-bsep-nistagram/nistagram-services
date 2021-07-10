package rs.ac.uns.ftn.nistagram.user.controllers.dtos.verification;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class VerificationRequestDTO {

    @NotNull
    private Long categoryId;
    @NotBlank
    private String imageUrl;

}
