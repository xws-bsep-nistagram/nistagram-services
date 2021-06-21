package rs.ac.uns.ftn.nistagram.content.repository.story;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.nistagram.content.domain.core.story.StoryHighlight;

import java.util.List;

@Repository
public interface StoryHighlightsRepository extends JpaRepository<StoryHighlight, Long> {

    @Query(value = "select h from StoryHighlight h where h.owner = ?1")
    List<StoryHighlight> getByUsername(String username);
}
