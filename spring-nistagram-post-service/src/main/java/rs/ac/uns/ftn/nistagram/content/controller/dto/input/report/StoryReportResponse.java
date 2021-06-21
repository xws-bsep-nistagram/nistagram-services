package rs.ac.uns.ftn.nistagram.content.controller.dto.input.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StoryReportResponse extends ReportResponse {

    private Long storyId;

    @Builder
    public StoryReportResponse(Long id, LocalDateTime creationDate,
                               String reportedBy, String reason, Long storyId) {
        super(id, creationDate, reportedBy, reason);
        this.storyId = storyId;
    }
}
