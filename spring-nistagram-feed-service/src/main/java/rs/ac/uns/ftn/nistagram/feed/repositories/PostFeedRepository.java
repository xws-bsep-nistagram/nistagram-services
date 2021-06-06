package rs.ac.uns.ftn.nistagram.feed.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.PostFeedEntry;


public interface PostFeedRepository extends JpaRepository<PostFeedEntry,Long> {
}
