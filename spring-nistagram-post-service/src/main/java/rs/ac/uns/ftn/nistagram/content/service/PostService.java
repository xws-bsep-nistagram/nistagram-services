package rs.ac.uns.ftn.nistagram.content.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
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
import rs.ac.uns.ftn.nistagram.content.messaging.event.content.PostCreatedEvent;
import rs.ac.uns.ftn.nistagram.content.messaging.event.content.PostDeletedEvent;
import rs.ac.uns.ftn.nistagram.content.messaging.event.notification.PostCommentedEvent;
import rs.ac.uns.ftn.nistagram.content.messaging.event.notification.PostDislikedEvent;
import rs.ac.uns.ftn.nistagram.content.messaging.event.notification.PostLikedEvent;
import rs.ac.uns.ftn.nistagram.content.messaging.event.notification.UserTaggedEvent;
import rs.ac.uns.ftn.nistagram.content.messaging.mappers.EventPayloadMapper;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.notification.PostCommentedEventPayload;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.notification.PostInteractionEventPayload;
import rs.ac.uns.ftn.nistagram.content.messaging.payload.post.PostPayloadType;
import rs.ac.uns.ftn.nistagram.content.repository.post.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    private final ApplicationEventPublisher publisher;
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

    @Transactional
    public Post create(Post post) {
        log.info("[POST][C][R][CALL={}]", post.getAuthor());

        post.setTime(LocalDateTime.now());
        Post savedPost = postRepository.save(post);
        log.info("[POST][C][C][CALL={}]", post.getAuthor());

        if (!post.isAd()) {
            if (post.usersTagged())
                publishUserTagged(post);

            publishPostCreated(post);
            log.info("[POST][C][P][CALL={}]", post.getAuthor());
        }

        return savedPost;
    }

    @Transactional
    public Post createForInfluencer(Post post) {
        log.info("[POST][C][R][TGT={}]", post.getAuthor());
        post.setAd(true);
        return create(post);
    }

    @Transactional
    public Post createForAgent(String agentUsername, Post post) {
        post.setAd(true);
        post.setAdApproved(true);
        post.setAuthor(agentUsername);
        return create(post);
    }

    @Transactional
    public void approveAdPost(String username, Long id) {
        log.info("[POST][C][R][CALL={}]", username);

        Post found = getByIdAsAdmin(id);
        if (!found.getAuthor().equals(username)) {
            throw new OwnershipException();
        }
        if (!found.isAd() || found.isAdApproved()) {
            throw new NistagramException("Cannot approve this ad post.");
        }
        found.setAdApproved(true);
        postRepository.save(found);
        publishPostCreated(found);
        log.info("[POST][C][P][CALL={}]", found.getAuthor());
    }

    private void publishUserTagged(Post post) {

        UserTaggedEvent event = new UserTaggedEvent(UUID.randomUUID().toString(),
                EventPayloadMapper.toPayload(post));

        log.info("Publishing an user tagged event {}", event);

        publisher.publishEvent(event);

    }

    private void publishPostCreated(Post post) {

        PostCreatedEvent event = new PostCreatedEvent(UUID.randomUUID().toString(),
                EventPayloadMapper.toPayload(post, PostPayloadType.POST_CREATED));

        log.info("Publishing a post created event {}", event);

        publisher.publishEvent(event);

    }

    @Transactional
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

            publishPostDeleted(post);

            log.info("[POST][D][P][CALL={}][ID={}]", caller, postId);
        }
    }

    @Transactional
    public void delete(long postId) {
        log.info("[POST][D][R][CALL=ADMIN][ID={}]", postId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Post with an id '%d' doesn't exist", postId)
                ));

        postRepository.delete(post);
        log.info("[POST][D][C][CALL=ADMIN][ID={}]", postId);

        publishPostDeleted(post);

        log.info("[POST][D][P][CALL=ADMIN][ID={}]", postId);

    }

    private void publishPostDeleted(Post post) {

        PostDeletedEvent event = new PostDeletedEvent(UUID.randomUUID().toString(),
                EventPayloadMapper.toPayload(post, PostPayloadType.POST_DELETED));

        log.info("Publishing a post deleted event {}", event);

        publisher.publishEvent(event);

    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public Post getById(long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Post with an id '%d' doesn't exist", postId)
                ));

        if (userClient.isPrivate(post.getAuthor()))
            throw new ProfileNotPublicException(post.getAuthor());

        return post;
    }

    @Transactional(readOnly = true)
    public Post getByIdAsAdmin(long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Post with an id '%d' doesn't exist", postId)
                ));
    }

    @Transactional(readOnly = true)
    public List<Post> getAllPublicByUsername(String username) {
        if (userClient.isPrivate(username)) {
            throw new ProfileNotPublicException(username);
        }
        return getByUsername(username);
    }

    @Transactional(readOnly = true)
    public List<Post> getByUsername(String caller, String username) {
        log.info("[POST][G][R][CALL={}][TGT={}]", caller, username);
        graphClient.assertBlocked(username, caller);
        graphClient.assertFollow(caller, username);
        List<Post> posts = postRepository.getByUsername(username);
        log.info("[POST][G][C][CALL={}][TGT={}]", caller, username);
        return posts;
    }

    @Transactional(readOnly = true)
    public List<Post> getByUsername(String username) {
        log.info("[POST][G][R][CALL={}][TGT={}]", "SYS", username);
        return postRepository.getByUsername(username);
    }

    @Transactional
    public void like(long postId, String caller) {
        log.info("[LIKE][C][R][CALL={}][ID={}]", caller, postId);
        addInteraction(postId, caller, UserInteraction.Sentiment.LIKE);
    }

    @Transactional
    public void deleteLike(long postId, String caller) {
        removeInteraction(postId, caller, UserInteraction.Sentiment.LIKE);
    }

    @Transactional
    public void dislike(long postId, String caller) {
        log.info("[DISLIKE][C][R][CALL={}][ID={}]", caller, postId);
        addInteraction(postId, caller, UserInteraction.Sentiment.DISLIKE);
    }

    @Transactional
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
            publishPostLiked(post, caller);
        else
            publishPostDisliked(post, caller);

    }

    private void publishPostLiked(Post post, String caller) {

        PostInteractionEventPayload payload = EventPayloadMapper.toPayload(post, caller);

        PostLikedEvent event = new PostLikedEvent(UUID.randomUUID().toString(),
                payload);

        log.info("Publishing a post liked event {}", event);

        publisher.publishEvent(event);

    }

    private void publishPostDisliked(Post post, String caller) {

        PostInteractionEventPayload payload = EventPayloadMapper.toPayload(post, caller);

        PostDislikedEvent event = new PostDislikedEvent(UUID.randomUUID().toString(),
                payload);

        log.info("Publishing a post disliked event {}", event);

        publisher.publishEvent(event);

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

    @Transactional
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
        publishPostCommented(commentedPost, comment);
        log.info("[COMMENT][C][C][CALL={}][ID={}]", comment.getAuthor(), postId);
    }

    private void publishPostCommented(Post post, Comment comment) {

        PostCommentedEventPayload payload = EventPayloadMapper.toPayload(post, comment);

        PostCommentedEvent event = new PostCommentedEvent(UUID.randomUUID().toString(),
                payload);

        log.info("Publishing a post commented event {}", event);

        publisher.publishEvent(event);

    }

    @Transactional
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

    @Transactional
    public List<SavedPost> getSaved(String caller) {
        log.info("[SAVE][G][R][CALL={}]", caller);
        return savedPostRepository.findByUser(caller);
    }

    @Transactional
    public void createCollection(String caller, String collectionName) {
        log.info("[COLLECTION][C][R][CALL={}][ID={}]", caller, collectionName);

        if (collectionRepository.getByUserAndName(caller, collectionName).isPresent())
            throw new ExistingEntityException("Collection", collectionName);

        collectionRepository.save(
                CustomPostCollection.builder().name(collectionName).owner(caller).build()
        );
        log.info("[COLLECTION][C][C][CALL={}][ID={}]", caller, collectionName);
    }

    @Transactional
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

    @Transactional
    public void removePostFromCollection(String caller, String collectionName, long postId) {
        log.info("[COLLECTION-POST][D][R][ID={}][CALL={}]", collectionName, caller);
        CustomPostCollection collection = collectionRepository.getByUserAndName(caller, collectionName).orElseThrow();

        log.info("[COLLECTION-POST][D][C][ID={}][CALL={}]", collectionName, caller);
        postInCollectionRepository.deletePostFromCollection(postId, collection.getId());
    }

    @Transactional
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

    @Transactional
    public void deleteCollection(String caller, String collectionName) {
        log.info("[COLLECTION][D][R][CALL={}][ID={}]", caller, collectionName);
        CustomPostCollection collection = collectionRepository.getByUserAndName(caller, collectionName).orElseThrow();

        postInCollectionRepository.deleteAllFromCollection(collection.getId());
        collectionRepository.delete(collection);

        log.info("[COLLECTION][D][C][CALL={}][ID={}]", caller, collectionName);
    }

    @Transactional
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

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public List<Post> searchByTagged(String username, String caller) {
        log.info("[SEARCH-TAG][G][R][PARAM={}]", username);
        graphClient.assertBlocked(caller, username);
        List<Post> foundPosts = postRepository.getByTagged(username);
        foundPosts = filterBlocked(foundPosts, caller);
        log.info("[SEARCH-TAG][C] Found {} posts", foundPosts.size());
        return foundPosts;
    }

    @Transactional(readOnly = true)
    public List<Post> getLikedAndDisliked(String username) {
        log.info("[LD][G][R][TGT={}]", username);
        List<Post> foundPosts = postRepository.getLikedAndDislikedByUser(username);
        log.info("[LD][G][C][TGT={}]", username);
        return foundPosts;
    }


    @Transactional(readOnly = true)
    public List<Post> getNonApproved(String username) {
        log.info("[POST][G][R][CALL={}]", username);
        List<Post> found = postRepository.getNonApprovedByUsername(username);
        log.info("[POST][G][C][CALL={}]", username);
        return found;
    }

}
