package rs.ac.uns.ftn.nistagram.content.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.collection.PostInCollection;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostInCollectionRepository extends JpaRepository<PostInCollection, Long> {

    @Query(value = "select p from PostInCollection p where p.post.id = ?1 and p.collection.id = ?2")
    Optional<PostInCollection> postFromCollection(long postId, long collectionId);

    @Query(value = "select p from PostInCollection p where p.collection.id = ?1")
    List<PostInCollection> allPostsFromCollection(long collectionId);

    @Transactional
    @Modifying
    @Query(
            nativeQuery = true,
            value = "delete from posts_in_collections where collection_id=:collectionId"
    )
    void deleteAllFromCollection(@Param("collectionId") long collectionId);

    @Modifying
    @Query(
            value = "delete from PostInCollection pc where pc.post.id = :postId and pc.collection.id in (:collectionIds)"
    )
    void deletePostFromUserCollections(@Param("postId") long postId, @Param("collectionIds") List<Long> collectionIds);

    @Modifying
    @Transactional
    @Query(value = "delete from PostInCollection pc where pc.post.id = ?1 and pc.collection.id = ?2")
    void deletePostFromCollection(long postId, long id);
}
