package rs.ac.uns.ftn.nistagram.content.controller.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.content.controller.dto.input.*;
import rs.ac.uns.ftn.nistagram.content.controller.dto.output.PostOverviewDTO;
import rs.ac.uns.ftn.nistagram.content.controller.dto.output.StoryHighlightOverviewDTO;
import rs.ac.uns.ftn.nistagram.content.controller.dto.output.StoryOverviewDTO;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.social.Comment;
import rs.ac.uns.ftn.nistagram.content.domain.core.story.MediaStory;
import rs.ac.uns.ftn.nistagram.content.domain.core.story.ShareStory;
import rs.ac.uns.ftn.nistagram.content.domain.core.story.Story;
import rs.ac.uns.ftn.nistagram.content.domain.core.story.StoryHighlight;

@Component
public class DomainDTOMapper {

    private final ModelMapper modelMapper;

    public DomainDTOMapper() {
        this.modelMapper = new ModelMapper();
    }

    public Post toDomain(PostCreationDTO dto) {
        Post post =  modelMapper.map(dto, Post.class);
        post.getMediaUrls().forEach(url -> url.setPost(post));
        post.getTags().forEach(hashTag -> hashTag.setPost(post));
        return post;
    }

    public PostOverviewDTO toDto(Post post) {

        PostOverviewDTO dto =  PostOverviewDTO
                                .builder()
                                .author(post.getAuthor())
                                .id(post.getId())
                                .location(post.getLocation())
                                .caption(post.getCaption())
                                .time(post.getTime())
                                .build();
        post.getMediaUrls().forEach(link -> dto.addMediaUrl(link.getUrl()));
        return dto;
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

        Post mockPost = new Post(); mockPost.setId(dto.getPostId());
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
            return toDto((ShareStory)story);
        if (story.getClass().equals(MediaStory.class))
            return toDto((MediaStory)story);
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
        return StoryHighlightOverviewDTO.builder().id(highlight.getId()).name(highlight.getName()).build();
    }
}
