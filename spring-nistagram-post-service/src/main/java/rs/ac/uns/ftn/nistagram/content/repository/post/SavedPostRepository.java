package rs.ac.uns.ftn.nistagram.content.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.collection.SavedPost;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedPostRepository extends JpaRepository<SavedPost, Long> {

    @Query(value = "select sp from SavedPost sp where sp.username = ?1 and sp.post.id = ?2")
    Optional<SavedPost> findByUserAndPost(String username, long postId);

    @Query(value = "select sp from SavedPost sp where sp.username = ?1")
    List<SavedPost> findByUser(String username);
}
