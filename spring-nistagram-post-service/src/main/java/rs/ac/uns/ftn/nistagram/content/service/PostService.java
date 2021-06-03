package rs.ac.uns.ftn.nistagram.content.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.social.UserInteraction;
import rs.ac.uns.ftn.nistagram.content.repository.PostRepository;
import rs.ac.uns.ftn.nistagram.content.repository.UserInteractionRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserInteractionRepository interactionRepository;

    public PostService(PostRepository postRepository, UserInteractionRepository interactionRepository) {
        this.postRepository = postRepository;
        this.interactionRepository = interactionRepository;
    }

    public void create(Post post) {
        post.setTime(LocalDateTime.now());
        postRepository.save(post);
    }

    public Post getById(long id) {
        return postRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void like(long id, String username) {
        addInteraction(id, username, UserInteraction.Sentiment.LIKE);
    }

    public void dislike(long id, String username) {
        addInteraction(id, username, UserInteraction.Sentiment.DISLIKE);
    }

    private void addInteraction(long postId, String username, UserInteraction.Sentiment sentiment) {
        Optional<UserInteraction> optionalInteraction = interactionRepository.findByPostAndUser(postId, username);
        if (optionalInteraction.isEmpty()) {
            Post post = postRepository.findById(postId).orElseThrow(RuntimeException::new);
            interactionRepository.save(
                    UserInteraction.builder()
                            .username(username)
                            .post(post)
                            .sentiment(sentiment)
                        .build()
            );
        }
        else {
            UserInteraction interaction = optionalInteraction.get();
            if (interaction.getSentiment() == sentiment)
                return;
            interaction.setSentiment(sentiment);
            interactionRepository.save(interaction);
        }
    }
}
