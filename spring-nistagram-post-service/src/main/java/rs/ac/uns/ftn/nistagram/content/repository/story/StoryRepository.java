package rs.ac.uns.ftn.nistagram.content.repository.story;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.nistagram.content.domain.core.story.Story;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

    @Query(value = "select s from Story s where s.author = ?1 and s.time > ?2")
    List<Story> getAllByUsernameAfterDate(String username, LocalDateTime time);

    @Query(value = "select s from Story s where s.author = ?1")
    List<Story> getAllByUsername(String username);

    @Query(value = "select s from Story s where s.author = ?1 and s.time > ?2 and s.closeFriends = false")
    List<Story> getNonCloseFriendsByUsernameAfterDate(String username, LocalDateTime time);
}
