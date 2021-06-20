package rs.ac.uns.ftn.nistagram.content.domain.core.report;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseReport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private LocalDateTime creationDate;
    private String reportedBy;
    private String reason;

    public BaseReport(BaseReport baseReport) {
        this.id = baseReport.id;
        this.creationDate = baseReport.creationDate;
        this.reportedBy = baseReport.reportedBy;
        this.reason = baseReport.reason;
    }

}
