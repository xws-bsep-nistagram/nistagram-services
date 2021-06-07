package rs.ac.uns.ftn.nistagram.content.domain.core.report;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
@Getter
@Setter
public abstract class BaseReport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private LocalDateTime creationDate;
    private String reportedBy;
    private String reason;
}
