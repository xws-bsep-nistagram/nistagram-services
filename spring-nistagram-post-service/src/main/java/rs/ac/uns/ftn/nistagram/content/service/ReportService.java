package rs.ac.uns.ftn.nistagram.content.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;
import rs.ac.uns.ftn.nistagram.content.domain.core.report.BaseReport;
import rs.ac.uns.ftn.nistagram.content.domain.core.report.PostReport;
import rs.ac.uns.ftn.nistagram.content.domain.core.report.StoryReport;
import rs.ac.uns.ftn.nistagram.content.domain.core.story.Story;
import rs.ac.uns.ftn.nistagram.content.repository.post.PostRepository;
import rs.ac.uns.ftn.nistagram.content.repository.report.PostReportRepository;
import rs.ac.uns.ftn.nistagram.content.repository.report.StoryReportRepository;
import rs.ac.uns.ftn.nistagram.content.repository.story.StoryRepository;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Service
@AllArgsConstructor
public class ReportService {

    private final StoryReportRepository storyReportRepository;
    private final PostReportRepository postReportRepository;
    private final PostRepository postRepository;
    private final StoryRepository storyRepository;


    public PostReport reportPost(BaseReport report, Long postId) {
        log.info("Reporting a post with an id: {}", postId);

        PostReport postReport = new PostReport(report);
        Post reportedPost = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Post with an id '%d' doesn't exist", postId)
                ));

        postReport.assignPost(reportedPost);
        reportedPost.addReport(postReport);
        postReport = postReportRepository.save(postReport);
        log.info("Post with an id: {} successfully reported", postId);
        return postReport;
    }

    public StoryReport reportStory(BaseReport report, Long storyId) {
        log.info("Reporting a story with an id: {}", storyId);

        StoryReport storyReport = new StoryReport(report);
        Story reportedStory = storyRepository.findById(storyId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Story with an id '%d' doesn't exist", storyId)
                ));

        storyReport.assignStory(reportedStory);
        reportedStory.addReport(storyReport);
        storyReport = storyReportRepository.save(storyReport);

        log.info("Story with an id: {} successfully reported", storyId);
        return storyReport;

    }
}
