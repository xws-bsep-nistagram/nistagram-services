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
import rs.ac.uns.ftn.nistagram.content.messaging.producers.NotificationProducer;
import rs.ac.uns.ftn.nistagram.content.repository.post.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final NotificationProducer notificationProducer;
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

    public Post create(Post post) {
        log.info("[POST][C][R][CALL={}]", post.getAuthor());

        post.setTime(LocalDateTime.now());
        Post savedPost = postRepository.save(post);
        log.info("[POST][C][C][CALL={}]", post.getAuthor());

        if (post.usersTagged())
            notificationProducer.publishUserTagged(post);

        contentProducer.publishPostCreated(post);
        log.info("[POST][C][P][CALL={}]", post.getAuthor());

        return savedPost;
    }

    public void delete(String caller, long postId) {
        log.info("[POST][D][R][CALL={}][ID={}]", caller, postId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Post with an id '%d' doesn't exist", postId)
                ));

        if (!post.getAuthor().equals(caller))
            throw new OwnershipException();
        else {
            postRepository.delete(post);
            log.info("[POST][D][C][CALL={}][ID={}]", caller, postId);

            contentProducer.publishPostDeleted(post);
            log.info("[POST][D][P][CALL={}][ID={}]", caller, postId);
        }
    }

    public void delete(long postId) {
        log.info("[POST][D][R][CALL=ADMIN][ID={}]", postId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Post with an id '%d' doesn't exist", postId)
                ));

        postRepository.delete(post);
        log.info("[POST][D][C][CALL=ADMIN][ID={}]", postId);

        contentProducer.publishPostDeleted(post);
        log.info("[POST][D][P][CALL=ADMIN][ID={}]", postId);

    }

    public Post getById(long postId, String caller) {
        log.info("[POST][G][R][CALL={}][ID={}]", caller, postId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Post with an id '%d' doesn't exist", postId)
                ));
        if (userClient.isPrivate(post.getAuthor()))
            graphClient.assertFollow(caller, post.getAuthor());

        log.info("[POST][G][C][CALL={}][ID={}]", caller, postId);
        return post;
    }

    public Post getById(long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Post with an id '%d' doesn't exist", postId)
                ));

        if (userClient.isPrivate(post.getAuthor()))
            throw new ProfileNotPublicException(post.getAuthor());

        return post;
    }

    public List<Post> getAllPublicByUsername(String username) {
        if (userClient.isPrivate(username)) {
            throw new ProfileNotPublicException(username);
        }
        List<Post> publicPosts = getByUsername(username);
        return publicPosts;
    }

    public List<Post> getByUsername(String caller, String username) {
        log.info("[POST][G][R][CALL={}][TGT={}]", caller, username);
        graphClient.assertBlocked(username, caller);
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

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Post with an id '%d' doesn't exist", postId)
                ));

        if (optionalInteraction.isEmpty()) {
            if (userClient.isPrivate(post.getAuthor()))
                graphClient.assertFollow(caller, post.getAuthor());
            log.info("[{}][C][C][CALL={}][ID={}]", sentiment, caller, postId);
            interactionRepository.save(
                    UserInteraction.builder()
                            .username(caller)
                            .post(post)
                            .sentiment(sentiment)
                            .build()
            );
        } else {
            UserInteraction interaction = optionalInteraction.get();
            if (interaction.getSentiment() != sentiment) {
                interaction.setSentiment(sentiment);
                interactionRepository.save(interaction);
            }
            log.info("[{}][C][C][CALL={}][ID={}]", sentiment, caller, postId);
        }

        if (sentiment.equals(UserInteraction.Sentiment.LIKE))
            notificationProducer.publishPostLiked(post, caller);
        else
            notificationProducer.publishPostDisliked(post, caller);

    }

    private void removeInteraction(long postId, String caller, UserInteraction.Sentiment sentiment) {
        Optional<UserInteraction> optionalInteraction = interactionRepository.findByPostAndUser(postId, caller);

        if (optionalInteraction.isPresent()) {
            UserInteraction interaction = optionalInteraction.get();
            if (interaction.getSentiment() == sentiment)
                interactionRepository.delete(interaction);

        } else
            throw new EntityNotFoundException("Interaction doesn't exist");

    }

    public void comment(Comment comment, long postId) {
        log.info("[COMMENT][C][R][ID={}][CALL={}]", postId, comment.getAuthor());

        Post commentedPost = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Post with an id '%d' doesn't exist", postId)
                ));

        if (userClient.isPrivate(commentedPost.getAuthor()))
            graphClient.assertFollow(comment.getAuthor(), commentedPost.getAuthor());

        comment.setPost(commentedPost);
        comment.setTime(LocalDateTime.now());

        commentRepository.save(comment);
        notificationProducer.publishPostCommented(commentedPost, comment);
        log.info("[COMMENT][C][C][CALL={}][ID={}]", comment.getAuthor(), postId);
    }

    public void save(String caller, long postId) {
        log.info("[SAVE][C][R][ID={}][CALL={}]", postId, caller);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Post with an id '%d' doesn't exist", postId)
                ));

        if (userClient.isPrivate(post.getAuthor()))
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

        SavedPost savedPost = savedPostRepository.findByUserAndPost(caller, postId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Saved post with an id '%d' doesn't exist", postId)
                ));

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
                        .orElseThrow(() -> new EntityNotFoundException(
                                String.format("Post collection with an id '%d' doesn't exist", postId)
                        ));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Post with an id '%d' doesn't exist", postId)
                ));

        if (postInCollectionRepository.postFromCollection(post.getId(), customPostCollection.getId()).isPresent())
            throw new NistagramException("Post already present in this collection");

        try {
            save(caller, postId); // This will throw if the post is already saved
        } catch (RuntimeException ignored) {
        }

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

        CustomPostCollection foundCollection = collectionRepository
                .getByUserAndName(caller, collectionName)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Collection with  name '%s' doesn't exist", collectionName)
                ));

        long collectionId = foundCollection.getId();

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

    public List<CustomPostCollection> getCollections(String caller) {
        List<CustomPostCollection> postCollections = collectionRepository.getByUser(caller);

        if (postCollections == null)
            return new ArrayList<>();

        return postCollections;
    }

    @Transactional(readOnly = true)
    public Long getPostCount(String username) {
        return postRepository.getCountByUsername(username);
    }

    public List<Post> searchByLocation(String street, String caller) {
        log.info("[SEARCH-LOC][G][R][PARAM={}]", street);
        List<Post> foundPosts = postRepository.getByLocation(street);
        foundPosts = filterBlocked(foundPosts, caller);
        log.info("Found {} posts", foundPosts.size());
        return foundPosts;
    }

    private List<Post> filterBlocked(List<Post> foundPosts, String caller) {
        return foundPosts
                .stream()
                .filter(post -> !graphClient.blocked(caller, post.getAuthor()))
                .collect(Collectors.toList());
    }

    public List<Post> searchByTagged(String username, String caller) {
        log.info("[SEARCH-TAG][G][R][PARAM={}]", username);
        graphClient.assertBlocked(caller, username);
        List<Post> foundPosts = postRepository.getByTagged(username);
        foundPosts = filterBlocked(foundPosts, caller);
        log.info("[SEARCH-TAG][C] Found {} posts", foundPosts.size());
        return foundPosts;
    }

    public List<Post> getLikedAndDisliked(String username) {
        log.info("[LD][G][R][TGT={}]", username);
        List<Post> foundPosts = postRepository.getLikedAndDislikedByUser(username);
        log.info("[LD][G][C][TGT={}]", username);
        return foundPosts;
    }


}
