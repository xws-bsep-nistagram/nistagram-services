package rs.ac.uns.ftn.nistagram.content.controller.dto.input.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostReportResponse extends ReportResponse {
    private Long postId;

    @Builder
    public PostReportResponse(Long id, LocalDateTime creationDate,
                              String reportedBy, String reason, Long postId) {
        super(id, creationDate, reportedBy, reason);
        this.postId = postId;
    }
}
