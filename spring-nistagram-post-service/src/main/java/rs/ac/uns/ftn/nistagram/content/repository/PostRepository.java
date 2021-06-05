package rs.ac.uns.ftn.nistagram.content.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
