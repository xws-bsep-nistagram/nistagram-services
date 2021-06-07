package rs.ac.uns.ftn.nistagram.content.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.content.communication.External;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.collection.CustomPostCollection;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.collection.PostInCollection;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.collection.SavedPost;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.social.Comment;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.social.UserInteraction;
import rs.ac.uns.ftn.nistagram.content.messaging.producers.ContentProducer;
import rs.ac.uns.ftn.nistagram.content.repository.post.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserInteractionRepository interactionRepository;
    private final CommentRepository commentRepository;
    private final SavedPostRepository savedPostRepository;
    private final CustomPostCollectionRepository collectionRepository;
    private final PostInCollectionRepository postInCollectionRepository;
    private final ContentProducer contentProducer;
    private final External.GraphClientWrapper graphClient;



    public void create(Post post) {
        post.setTime(LocalDateTime.now());
        postRepository.save(post);
        contentProducer.publishPostCreated(post);
    }

    public void delete(String caller, long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        if (!post.getAuthor().equals(caller))
            throw new RuntimeException("You are not the owner of this post.");
        else {
            postRepository.delete(post);
            contentProducer.publishPostDeleted(post);
        }
    }

    public Post getById(long postId, String caller) {
        Post post = postRepository.findById(postId).orElseThrow();
        graphClient.assertFollow(caller, post.getAuthor());
        return post;
    }

    public List<Post> getByUsername(String caller, String username) {
        graphClient.assertFollow(caller, username);
        return postRepository.getByUsername(username);
    }

    public List<Post> getByUsername(String username) {
        return postRepository.getByUsername(username);
    }

    public void like(long postId, String caller) {
        addInteraction(postId, caller, UserInteraction.Sentiment.LIKE);
    }

    public void dislike(long postId, String caller) {
        addInteraction(postId, caller, UserInteraction.Sentiment.DISLIKE);
    }

    private void addInteraction(long postId, String caller, UserInteraction.Sentiment sentiment) {
        Optional<UserInteraction> optionalInteraction = interactionRepository.findByPostAndUser(postId, caller);
        if (optionalInteraction.isEmpty()) {
            Post post = postRepository.findById(postId).orElseThrow();
            graphClient.assertFollow(caller, post.getAuthor());
            interactionRepository.save(
                    UserInteraction.builder()
                            .username(caller)
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

    public void comment(Comment comment, long postId) {
        Post commentedPost = postRepository.findById(postId).orElseThrow();
        graphClient.assertFollow(comment.getAuthor(), commentedPost.getAuthor());

        comment.setPost(commentedPost);
        comment.setTime(LocalDateTime.now());

        commentRepository.save(comment);
    }

    public void save(String caller, long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        graphClient.assertFollow(caller, post.getAuthor());

        Optional<SavedPost> savedPost = savedPostRepository.findByUserAndPost(caller, postId);
        if (savedPost.isPresent())
            throw new RuntimeException("Post already saved");

        savedPostRepository.save(
                SavedPost.builder().post(post).username(caller).build()
        );
    }

    @Transactional
    public void unsave(String caller, long postId) {
        SavedPost savedPost = savedPostRepository.findByUserAndPost(caller, postId).orElseThrow();
        savedPostRepository.delete(savedPost);

        // Fetch collection IDs from this user
        List<Long> collectionIds = collectionRepository.getIdsByUser(caller);
        if (collectionIds.isEmpty()) return;

        postInCollectionRepository.deletePostFromUserCollections(postId, collectionIds);
    }

    public List<SavedPost> getSaved(String caller) {
        return savedPostRepository.findByUser(caller);
    }

    public void createCollection(String caller, String collectionName) {
        if (collectionRepository.getByUserAndName(caller, collectionName).isPresent())
            throw new RuntimeException("Collection '" + collectionName + "' already exists.");

        collectionRepository.save(
            CustomPostCollection.builder().name(collectionName).owner(caller).build()
        );
    }

    public void addPostToCollection(String caller, String collectionName, long postId) {
        CustomPostCollection customPostCollection =
                collectionRepository.getByUserAndName(caller, collectionName)
                        .orElseThrow();

        Post post = postRepository.findById(postId).orElseThrow();
        if (postInCollectionRepository.postFromCollection(post.getId(), customPostCollection.getId()).isPresent())
            throw new RuntimeException("Post already present in this collection");

        try {
            save(caller, postId); // This will throw if the post is already saved
        }
        catch (RuntimeException ignored) {}

        postInCollectionRepository.save(PostInCollection.builder().collection(customPostCollection).post(post).build());
    }

    public void removePostFromCollection(String caller, String collectionName, long postId) {
        CustomPostCollection collection = collectionRepository.getByUserAndName(caller, collectionName).orElseThrow();
        postInCollectionRepository.deletePostFromCollection(postId, collection.getId());
    }

    public List<PostInCollection> getAllFromCollection(String caller, String collectionName) {
        long collectionId =
                collectionRepository.getByUserAndName(caller, collectionName).orElseThrow().getId();
        return postInCollectionRepository.allPostsFromCollection(collectionId);
    }

    public void deleteCollection(String caller, String collectionName) {
        CustomPostCollection collection = collectionRepository.getByUserAndName(caller, collectionName).orElseThrow();

        postInCollectionRepository.deleteAllFromCollection(collection.getId());
        collectionRepository.delete(collection);
    }
}
