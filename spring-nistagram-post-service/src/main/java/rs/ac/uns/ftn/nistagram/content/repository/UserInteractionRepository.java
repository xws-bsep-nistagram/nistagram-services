package rs.ac.uns.ftn.nistagram.content.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.social.UserInteraction;

import java.util.Optional;

@Repository
public interface UserInteractionRepository extends JpaRepository<UserInteraction, Long> {

    @Query("select i from UserInteraction i where i.post.id = ?1 and i.username = ?2")
    Optional<UserInteraction> findByPostAndUser(long postId, String username);
}
