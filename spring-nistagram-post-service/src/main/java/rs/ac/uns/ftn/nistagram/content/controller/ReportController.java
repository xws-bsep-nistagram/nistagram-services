package rs.ac.uns.ftn.nistagram.content.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.content.controller.dto.input.report.PostReportResponse;
import rs.ac.uns.ftn.nistagram.content.controller.dto.input.report.ReportRequest;
import rs.ac.uns.ftn.nistagram.content.controller.dto.input.report.StoryReportResponse;
import rs.ac.uns.ftn.nistagram.content.controller.mapper.DomainDTOMapper;
import rs.ac.uns.ftn.nistagram.content.domain.core.report.PostReport;
import rs.ac.uns.ftn.nistagram.content.domain.core.report.StoryReport;
import rs.ac.uns.ftn.nistagram.content.service.ReportService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("api/content/report")
public class ReportController {

    private final ReportService reportService;
    private final DomainDTOMapper mapper;

    @PostMapping("post/{postId}")
    public ResponseEntity<?> reportPost(@PathVariable Long postId,
                                        @RequestHeader("username") String username,
                                        @Valid @RequestBody ReportRequest reportRequest) {

        PostReport report = reportService
                .reportPost(mapper.toDomain(reportRequest, username), postId);
        return ResponseEntity.ok(mapper.toDto(report));
    }

    @PostMapping("story/{storyId}")
    public ResponseEntity<?> reportStory(@PathVariable Long storyId,
                                         @RequestHeader("username") String username,
                                         @Valid @RequestBody ReportRequest reportRequest) {

        StoryReport report = reportService
                .reportStory(mapper.toDomain(reportRequest, username), storyId);
        return ResponseEntity.ok(mapper.toDto(report));
    }

    @GetMapping("post")
    public ResponseEntity<?> getAllPostReports() {
        List<PostReportResponse> postReportResponses = reportService.getReportedPosts()
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(postReportResponses);
    }

    @GetMapping("story")
    public ResponseEntity<?> getAllStoryReports() {
        List<StoryReportResponse> storyReportResponses = reportService.getReportedStories()
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(storyReportResponses);
    }

    @DeleteMapping("post/{postReportId}")
    public ResponseEntity<?> deletePostReport(@PathVariable Long postReportId) {
        PostReportResponse deletedPostReport = mapper.toDto(reportService.deletePostReport(postReportId));

        return ResponseEntity.ok(deletedPostReport);
    }

    @DeleteMapping("story/{storyReportId}")
    public ResponseEntity<?> deleteStoryReport(@PathVariable Long storyReportId) {
        StoryReportResponse deletedStoryReport = mapper.toDto(reportService.deleteStoryReport(storyReportId));

        return ResponseEntity.ok(deletedStoryReport);
    }

}
