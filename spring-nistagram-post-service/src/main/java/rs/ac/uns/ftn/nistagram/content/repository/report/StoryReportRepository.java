package rs.ac.uns.ftn.nistagram.content.repository.report;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.nistagram.content.domain.core.report.StoryReport;

public interface StoryReportRepository extends JpaRepository<StoryReport, Long> {
}
