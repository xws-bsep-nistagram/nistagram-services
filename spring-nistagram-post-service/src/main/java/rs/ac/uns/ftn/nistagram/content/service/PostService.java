package rs.ac.uns.ftn.nistagram.content.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.collection.SavedPost;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.social.Comment;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.social.UserInteraction;
import rs.ac.uns.ftn.nistagram.content.repository.CommentRepository;
import rs.ac.uns.ftn.nistagram.content.repository.PostRepository;
import rs.ac.uns.ftn.nistagram.content.repository.SavedPostRepository;
import rs.ac.uns.ftn.nistagram.content.repository.UserInteractionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserInteractionRepository interactionRepository;
    private final CommentRepository commentRepository;
    private final SavedPostRepository savedPostRepository;

    public PostService(
            PostRepository postRepository,
            UserInteractionRepository interactionRepository,
            CommentRepository commentRepository,
            SavedPostRepository savedPostRepository
    ) {
        this.postRepository = postRepository;
        this.interactionRepository = interactionRepository;
        this.commentRepository = commentRepository;
        this.savedPostRepository = savedPostRepository;
    }

    public void create(Post post) {
        post.setTime(LocalDateTime.now());
        postRepository.save(post);
    }

    public Post getById(long postId) {
        return postRepository.findById(postId).orElseThrow(RuntimeException::new);
    }


    // TODO Check whether the user follows the author of this post!
    public void like(long postId, String username) {
        addInteraction(postId, username, UserInteraction.Sentiment.LIKE);
    }

    // TODO Check whether the user follows the author of this post!
    public void dislike(long postId, String username) {
        addInteraction(postId, username, UserInteraction.Sentiment.DISLIKE);
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

    // TODO Check whether the user follows the author of this post
    public void comment(Comment comment, long postId) {
        comment.setPost(postRepository.findById(postId).orElseThrow(RuntimeException::new));
        comment.setTime(LocalDateTime.now());
        commentRepository.save(comment);
    }

    // TODO Check whether the user follows the author of this post
    public void save(String username, long postId) {
        Post post = postRepository.findById(postId).orElseThrow(RuntimeException::new);
        Optional<SavedPost> savedPost = savedPostRepository.findByUserAndPost(username, postId);
        if (savedPost.isPresent())
            throw new RuntimeException("Post already saved");
        savedPostRepository.save(
                SavedPost.builder().post(post).username(username).build()
        );
    }

    public List<SavedPost> getSaved(String username) {
        return savedPostRepository.findByUser(username);
    }
}
