package rs.ac.uns.ftn.nistagram.content.controller.dto.input.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {

    private long id;
    private LocalDateTime creationDate;
    private String reportedBy;
    private String reason;

}
