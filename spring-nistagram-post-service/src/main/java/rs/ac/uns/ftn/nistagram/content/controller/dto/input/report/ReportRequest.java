package rs.ac.uns.ftn.nistagram.content.controller.dto.input.report;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class ReportRequest {

    @NotEmpty
    @NotNull
    private String reason;
}
