package rs.ac.uns.ftn.nistagram.content.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.social.Tag;
import rs.ac.uns.ftn.nistagram.content.domain.core.story.Story;
import rs.ac.uns.ftn.nistagram.content.repository.post.PostRepository;
import rs.ac.uns.ftn.nistagram.content.repository.post.TagRepository;
import rs.ac.uns.ftn.nistagram.content.repository.story.StoryRepository;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class MessagingEventHandler {

    private final PostRepository postRepository;
    private final StoryRepository storyRepository;
    private final TagRepository tagRepository;

    public void handleUserBanned(String username) {
        log.info("Handling user banned request for an user {}", username);

        List<Post> postsByUser = postRepository.getByUsername(username);
        postsByUser.forEach(postRepository::delete);
        log.info("All of the posts by {} have been deleted", username);

        List<Story> storiesByUser = storyRepository.getAllByUsername(username);
        storiesByUser.forEach(storyRepository::delete);
        log.info("All of the stories by {} have been deleted", username);

        List<Tag> tags = tagRepository.findByTag(username);
        tags.forEach(tagRepository::delete);
        log.info("Tags containing user {} have been deleted", username);
    }

}
