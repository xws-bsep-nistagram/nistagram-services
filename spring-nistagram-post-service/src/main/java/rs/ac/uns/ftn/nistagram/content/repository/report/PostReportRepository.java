package rs.ac.uns.ftn.nistagram.content.repository.report;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.nistagram.content.domain.core.report.PostReport;

public interface PostReportRepository extends JpaRepository<PostReport, Long> {
}
