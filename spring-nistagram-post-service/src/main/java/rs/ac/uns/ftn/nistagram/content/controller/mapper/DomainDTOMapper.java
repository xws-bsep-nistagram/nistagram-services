package rs.ac.uns.ftn.nistagram.content.controller.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.content.controller.dto.input.CommentCreationDTO;
import rs.ac.uns.ftn.nistagram.content.controller.dto.input.PostCreationDTO;
import rs.ac.uns.ftn.nistagram.content.controller.dto.output.PostOverviewDTO;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.social.Comment;

@Component
public class DomainDTOMapper {

    private final ModelMapper modelMapper;

    public DomainDTOMapper() {
        this.modelMapper = new ModelMapper();
    }

    public Post toDomain(PostCreationDTO dto) {
        Post post =  modelMapper.map(dto, Post.class);
        post.getMediaUrls().forEach(url -> url.setPost(post));
        post.getHashTags().forEach(hashTag -> hashTag.setPost(post));
        return post;
    }

    public PostOverviewDTO toDto(Post post) {
        PostOverviewDTO dto =  modelMapper.map(post, PostOverviewDTO.class);
        // Manual prevention of SO-exception due to circular references
        dto.getMediaUrls().forEach(url -> url.setPost(null));
        return dto;
    }

    public Comment toDomain(CommentCreationDTO dto) {
        return Comment.builder()
                .author(dto.getAuthor())
                .text(dto.getText())
            .build();
    }
}
