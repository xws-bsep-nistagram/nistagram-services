package rs.ac.uns.ftn.nistagram.content.controller.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.content.controller.dto.input.*;
import rs.ac.uns.ftn.nistagram.content.controller.dto.input.report.PostReportResponse;
import rs.ac.uns.ftn.nistagram.content.controller.dto.input.report.ReportRequest;
import rs.ac.uns.ftn.nistagram.content.controller.dto.input.report.StoryReportResponse;
import rs.ac.uns.ftn.nistagram.content.controller.dto.output.*;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.collection.CustomPostCollection;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.social.Comment;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.social.UserInteraction;
import rs.ac.uns.ftn.nistagram.content.domain.core.report.BaseReport;
import rs.ac.uns.ftn.nistagram.content.domain.core.report.PostReport;
import rs.ac.uns.ftn.nistagram.content.domain.core.report.StoryReport;
import rs.ac.uns.ftn.nistagram.content.domain.core.story.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DomainDTOMapper {

    private final ModelMapper modelMapper;

    public DomainDTOMapper() {
        this.modelMapper = new ModelMapper();
    }

    public Post toDomain(PostCreationDTO dto) {
        Post post = modelMapper.map(dto, Post.class);
        post.getMediaUrls().forEach(url -> url.setPost(post));
        post.getTags().forEach(hashTag -> hashTag.setPost(post));
        return post;
    }

    public PostCollectionDTO toDto(CustomPostCollection postCollection) {
        return PostCollectionDTO
                .builder()
                .name(postCollection.getName())
                .id(postCollection.getId())
                .build();
    }

    public PostOverviewDTO toDto(Post post) {

        PostOverviewDTO dto = PostOverviewDTO
                .builder()
                .author(post.getAuthor())
                .id(post.getId())
                .location(post.getLocation())
                .caption(post.getCaption())
                .time(post.getTime())
                .ad(post.isAd())
                .build();

        if (post.getMediaUrls() != null)
            post.getMediaUrls().forEach(link -> dto.addMediaUrl(link.getUrl()));
        if (post.getComments() != null)
            post.getComments().forEach(comment -> dto.addComment(toDto(comment)));
        if (post.getUserInteractions() != null)
            post.getUserInteractions().forEach(interaction -> dto.addInteraction(toDto(interaction)));
        if (post.getTags() != null)
            post.getTags().forEach(tag -> dto.addTag(tag.getTag()));

        return dto;
    }

    public CommentOverviewDTO toDto(Comment comment) {
        return CommentOverviewDTO.builder().author(comment.getAuthor()).text(comment.getText()).build();
    }

    public UserInteractionOverviewDTO toDto(UserInteraction interaction) {
        return UserInteractionOverviewDTO.builder()
                .author(interaction.getUsername()).sentiment(interaction.getSentiment())
                .build();
    }

    public Comment toDomain(CommentCreationDTO dto) {
        return Comment.builder()
                .author(dto.getAuthor())
                .text(dto.getText())
                .build();
    }

    // Story

    public Story toDomain(StoryCreationDTO dto, boolean isReshare) {
        return
                isReshare ?
                        toShareStory((ShareStoryCreationDTO) dto)
                        : toMediaStory((MediaStoryCreationDTO) dto);
    }

    private ShareStory toShareStory(ShareStoryCreationDTO dto) {
        ShareStory shareStory = new ShareStory();

        Post mockPost = new Post();
        mockPost.setId(dto.getPostId());
        shareStory.setSharedPost(mockPost);

        populateDefaultStory(shareStory, dto);
        return shareStory;
    }

    private MediaStory toMediaStory(MediaStoryCreationDTO dto) {
        MediaStory mediaStory = new MediaStory();
        mediaStory.setMediaUrl(dto.getMediaUrl());
        populateDefaultStory(mediaStory, dto);
        return mediaStory;
    }

    private void populateDefaultStory(Story story, StoryCreationDTO dto) {
        story.setCloseFriends(dto.isCloseFriends());
        story.setAuthor(dto.getAuthor());
        story.setCaption(dto.getCaption());
        story.setLocation(dto.getLocation());
    }

    // StoryOverviewDTO

    public StoryOverviewDTO toDto(Story story) {
        if (story.getClass().equals(ShareStory.class))
            return toDto((ShareStory) story);
        if (story.getClass().equals(MediaStory.class))
            return toDto((MediaStory) story);
        else throw new RuntimeException("Story type " + story.getClass() + " not recognized.");
    }

    private StoryOverviewDTO toDto(ShareStory story) {
        StoryOverviewDTO dto = createDefaultStoryOverview(story);
        dto.setReshare(true);
        dto.setPost(toDto(story.getSharedPost()));
        return dto;
    }

    private StoryOverviewDTO toDto(MediaStory story) {
        StoryOverviewDTO dto = createDefaultStoryOverview(story);
        dto.setMediaUrl(story.getMediaUrl());
        return dto;
    }

    private StoryOverviewDTO createDefaultStoryOverview(Story story) {
        StoryOverviewDTO dto = modelMapper.map(story, StoryOverviewDTO.class);
        dto.setCloseFriends(story.isCloseFriends());
        return dto;
    }

    // Story highlights

    public StoryHighlightOverviewDTO toDto(StoryHighlight highlight) {
        List<StoryOverviewDTO> storyDtos = null;
        if (highlight.getStories() != null) {
            List<Story> stories = highlight.getStories().stream()
                    .map(HighlightedStory::getStory)
                    .collect(Collectors.toList());
            storyDtos = stories.stream().map(story -> toDto(story)).collect(Collectors.toList());
        }
        return StoryHighlightOverviewDTO.builder()
                .id(highlight.getId())
                .name(highlight.getName())
                .stories(storyDtos)
                .build();
    }

    public PostCountDTO toDto(Long postCount) {
        PostCountDTO dto = new PostCountDTO();
        dto.setPostCount(postCount);
        return dto;
    }

    //Reports

    public BaseReport toDomain(ReportRequest request, String username) {
        return BaseReport
                .builder()
                .creationDate(LocalDateTime.now())
                .reason(request.getReason())
                .reportedBy(username)
                .build();
    }

    public PostReportResponse toDto(PostReport postReport) {
        return PostReportResponse
                .builder()
                .postId(postReport.getReportedPost().getId())
                .reason(postReport.getReason())
                .reportedBy(postReport.getReportedBy())
                .creationDate(postReport.getCreationDate())
                .id(postReport.getId())
                .build();
    }

    public StoryReportResponse toDto(StoryReport storyReport) {
        return StoryReportResponse
                .builder()
                .storyId(storyReport.getReportedStory().getId())
                .reason(storyReport.getReason())
                .reportedBy(storyReport.getReportedBy())
                .creationDate(storyReport.getCreationDate())
                .id(storyReport.getId())
                .build();
    }
}
