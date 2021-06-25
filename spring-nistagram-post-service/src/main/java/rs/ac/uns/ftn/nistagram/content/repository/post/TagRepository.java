package rs.ac.uns.ftn.nistagram.content.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.social.Tag;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("select t from Tag t where t.tag = ?1")
    List<Tag> findByTag(String tag);

}
