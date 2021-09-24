package rs.ac.uns.ftn.nistagram.content.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public void handleUserBanned(String username) {
        log.info("Handling user banned request for an user with an username '{}'", username);

        hidePostsByUser(username);

        hideStoriesByUser(username);

        hideTagsContainingUser(username);
    }

    private void hideTagsContainingUser(String username) {
        List<Tag> tags = tagRepository.findByTag(username);
        tags.forEach(tag -> {
            tag.hide();
            tagRepository.save(tag);
        });
        log.info("Tags containing user '{}' have been hidden", username);
    }

    private void hideStoriesByUser(String username) {
        List<Story> storiesByUser = storyRepository.getAllByUsername(username);
        storiesByUser.forEach(story -> {
            story.hide();
            storyRepository.save(story);
        });
        log.info("All of the stories by '{}' have been hidden", username);
    }

    private void hidePostsByUser(String username) {
        List<Post> postsByUser = postRepository.getByUsername(username);
        postsByUser.forEach(post -> {
            post.hide();
            postRepository.save(post);
        });
        log.info("All of the posts by '{}' have been hidden", username);
    }

}
