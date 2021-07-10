package rs.ac.uns.ftn.nistagram.feed.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.PostFeedEntry;

import java.util.List;


public interface PostFeedRepository extends JpaRepository<PostFeedEntry, Long> {
    @Query("select pfe from PostFeedEntry pfe join pfe.users user where user.username =:username")
    List<PostFeedEntry> findAllByUsername(String username);

    @Query("select pfe from PostFeedEntry pfe where pfe.publisher =:username")
    List<PostFeedEntry> findAllByPublisher(String username);

    void deleteAllByPostId(Long postId);

}
