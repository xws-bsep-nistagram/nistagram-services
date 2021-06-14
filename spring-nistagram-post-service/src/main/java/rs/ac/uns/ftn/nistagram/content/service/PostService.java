package rs.ac.uns.ftn.nistagram.content.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.content.communication.External;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.collection.CustomPostCollection;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.collection.PostInCollection;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.collection.SavedPost;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.social.Comment;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.social.UserInteraction;
import rs.ac.uns.ftn.nistagram.content.exception.ExistingEntityException;
import rs.ac.uns.ftn.nistagram.content.exception.NistagramException;
import rs.ac.uns.ftn.nistagram.content.exception.OwnershipException;
import rs.ac.uns.ftn.nistagram.content.exception.ProfileNotPublicException;
import rs.ac.uns.ftn.nistagram.content.messaging.producers.ContentProducer;
import rs.ac.uns.ftn.nistagram.content.repository.post.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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
    private final External.UserClientWrapper userClient;

    // Logging rules:
    // [CLASS][CRUD][STATUS][CALLER][TARGET][ID]
    // [CLASS] -> Self-explanatory,
    // [STATUS] -> [R] = Requested, [C] = Completed, [P] = Published
    // [CALL=____] Username of the caller
    // [TGT=_____] Username of the user whose data is being accessed
    // [ID=______] Data id
    // Example, '[POST][C][R][CALL=joe]' means, POST Creation Request by user Joe
    //          '[POST][D][C][ID=25]' means, POST with Id=25 Deletion Complete

    public void create(Post post) {
        log.info("[POST][C][R][CALL={}]", post.getAuthor());

        post.setTime(LocalDateTime.now());
        postRepository.save(post);
        log.info("[POST][C][C][CALL={}]", post.getAuthor());

        contentProducer.publishPostCreated(post);
        log.info("[POST][C][P][CALL={}]", post.getAuthor());
    }

    public void delete(String caller, long postId) {
        log.info("[POST][D][R][CALL={}][ID={}]", caller, postId);

        Post post = postRepository.findById(postId).orElseThrow();
        if (!post.getAuthor().equals(caller))
            throw new OwnershipException();
        else {
            postRepository.delete(post);
            log.info("[POST][D][C][CALL={}][ID={}]", caller, postId);

            contentProducer.publishPostDeleted(post);
            log.info("[POST][D][P][CALL={}][ID={}]", caller, postId);
        }
    }

    public Post getById(long postId, String caller) {
        log.info("[POST][G][R][CALL={}][ID={}]", caller, postId);

        Post post = postRepository.findById(postId).orElseThrow();
        if(userClient.isPrivate(post.getAuthor()))
            graphClient.assertFollow(caller, post.getAuthor());

        log.info("[POST][G][C][CALL={}][ID={}]", caller, postId);
        return post;
    }

    public Post getById(long postId) {
        Post post = postRepository.findById(postId).orElseThrow();

        if(userClient.isPrivate(post.getAuthor()))
            throw new ProfileNotPublicException(post.getAuthor());

        return post;
    }

    public List<Post> getByUsername(String caller, String username) {
        log.info("[POST][G][R][CALL={}][TGT={}]", caller, username);
        graphClient.assertFollow(caller, username);
        List<Post> posts = postRepository.getByUsername(username);
        log.info("[POST][G][C][CALL={}][TGT={}]", caller, username);
        return posts;
    }

    public List<Post> getByUsername(String username) {
        log.info("[POST][G][R][CALL={}][TGT={}]", "SYS", username);
        return postRepository.getByUsername(username);
    }

    public void like(long postId, String caller) {
        log.info("[LIKE][C][R][CALL={}][ID={}]", caller, postId);
        addInteraction(postId, caller, UserInteraction.Sentiment.LIKE);
    }
    public void deleteLike(long postId, String caller) {
        removeInteraction(postId, caller, UserInteraction.Sentiment.LIKE);
    }

    public void dislike(long postId, String caller) {
        log.info("[DISLIKE][C][R][CALL={}][ID={}]", caller, postId);
        addInteraction(postId, caller, UserInteraction.Sentiment.DISLIKE);
    }

    public void deleteDislike(long postId, String caller) {
        removeInteraction(postId, caller, UserInteraction.Sentiment.DISLIKE);
    }

    private void addInteraction(long postId, String caller, UserInteraction.Sentiment sentiment) {
        Optional<UserInteraction> optionalInteraction = interactionRepository
                .findByPostAndUser(postId, caller);
        if (optionalInteraction.isEmpty()) {
            Post post = postRepository.findById(postId).orElseThrow();
            if(userClient.isPrivate(post.getAuthor()))
                graphClient.assertFollow(caller, post.getAuthor());
            log.info("[{}][C][C][CALL={}][ID={}]", sentiment, caller, postId);
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
            if (interaction.getSentiment() != sentiment) {
                interaction.setSentiment(sentiment);
                interactionRepository.save(interaction);
            }
            log.info("[{}][C][C][CALL={}][ID={}]", sentiment, caller, postId);
        }
    }
    private void removeInteraction(long postId, String caller, UserInteraction.Sentiment sentiment) {
        Optional<UserInteraction> optionalInteraction = interactionRepository.findByPostAndUser(postId, caller);

        if (optionalInteraction.isPresent()) {
            UserInteraction interaction = optionalInteraction.get();
            if(interaction.getSentiment() == sentiment)
                interactionRepository.delete(interaction);

        }
        else
            throw new EntityNotFoundException("Interaction doesn't exist");

    }

    public void comment(Comment comment, long postId) {
        log.info("[COMMENT][C][R][ID={}][CALL={}]", postId, comment.getAuthor());

        Post commentedPost = postRepository.findById(postId).orElseThrow();
        if(userClient.isPrivate(commentedPost.getAuthor()))
            graphClient.assertFollow(comment.getAuthor(), commentedPost.getAuthor());

        comment.setPost(commentedPost);
        comment.setTime(LocalDateTime.now());

        commentRepository.save(comment);
        log.info("[COMMENT][C][C][CALL={}][ID={}]", comment.getAuthor(), postId);
    }

    public void save(String caller, long postId) {
        log.info("[SAVE][C][R][ID={}][CALL={}]", postId, caller);

        Post post = postRepository.findById(postId).orElseThrow();
        if(userClient.isPrivate(post.getAuthor()))
            graphClient.assertFollow(caller, post.getAuthor());

        Optional<SavedPost> savedPost = savedPostRepository.findByUserAndPost(caller, postId);
        if (savedPost.isPresent())
            throw new NistagramException("Post already saved");

        log.info("[SAVE][C][C][ID={}][CALL={}]", postId, caller);
        savedPostRepository.save(
                SavedPost.builder().post(post).username(caller).build()
        );
    }

    @Transactional
    public void unsave(String caller, long postId) {
        log.info("[SAVE][D][R][ID={}][CALL={}]", postId, caller);

        SavedPost savedPost = savedPostRepository.findByUserAndPost(caller, postId).orElseThrow();
        savedPostRepository.delete(savedPost);

        // Fetch collection IDs from this user
        List<Long> collectionIds = collectionRepository.getIdsByUser(caller);
        if (!collectionIds.isEmpty())
            postInCollectionRepository.deletePostFromUserCollections(postId, collectionIds);

        log.info("[SAVE][D][C][ID={}][CALL={}]", postId, caller);
    }

    public List<SavedPost> getSaved(String caller) {
        log.info("[SAVE][G][R][CALL={}]", caller);
        return savedPostRepository.findByUser(caller);
    }

    public void createCollection(String caller, String collectionName) {
        log.info("[COLLECTION][C][R][CALL={}][ID={}]", caller, collectionName);

        if (collectionRepository.getByUserAndName(caller, collectionName).isPresent())
            throw new ExistingEntityException("Collection", collectionName);

        collectionRepository.save(
            CustomPostCollection.builder().name(collectionName).owner(caller).build()
        );
        log.info("[COLLECTION][C][C][CALL={}][ID={}]", caller, collectionName);
    }

    public void addPostToCollection(String caller, String collectionName, long postId) {
        log.info("[COLLECTION-POST][C][R][ID={}][CALL={}]", collectionName, caller);

        CustomPostCollection customPostCollection =
                collectionRepository.getByUserAndName(caller, collectionName)
                        .orElseThrow();

        Post post = postRepository.findById(postId).orElseThrow();
        if (postInCollectionRepository.postFromCollection(post.getId(), customPostCollection.getId()).isPresent())
            throw new NistagramException("Post already present in this collection");

        try {
            save(caller, postId); // This will throw if the post is already saved
        }
        catch (RuntimeException ignored) {}

        postInCollectionRepository.save(PostInCollection.builder().collection(customPostCollection).post(post).build());
        log.info("[COLLECTION-POST][C][C][ID={}][CALL={}]", collectionName, caller);
    }

    public void removePostFromCollection(String caller, String collectionName, long postId) {
        log.info("[COLLECTION-POST][D][R][ID={}][CALL={}]", collectionName, caller);
        CustomPostCollection collection = collectionRepository.getByUserAndName(caller, collectionName).orElseThrow();

        log.info("[COLLECTION-POST][D][C][ID={}][CALL={}]", collectionName, caller);
        postInCollectionRepository.deletePostFromCollection(postId, collection.getId());
    }

    public List<PostInCollection> getAllFromCollection(String caller, String collectionName) {
        log.info("[COLLECTION-POST][G][R][ID={}][CALL={}]", collectionName, caller);

        long collectionId =
                collectionRepository.getByUserAndName(caller, collectionName).orElseThrow().getId();

        log.info("[COLLECTION-POST][G][C][ID={}][CALL={}]", collectionName, caller);
        return postInCollectionRepository.allPostsFromCollection(collectionId);
    }

    public void deleteCollection(String caller, String collectionName) {
        log.info("[COLLECTION][D][R][CALL={}][ID={}]", caller, collectionName);
        CustomPostCollection collection = collectionRepository.getByUserAndName(caller, collectionName).orElseThrow();

        postInCollectionRepository.deleteAllFromCollection(collection.getId());
        collectionRepository.delete(collection);

        log.info("[COLLECTION][D][C][CALL={}][ID={}]", caller, collectionName);
    }

}
