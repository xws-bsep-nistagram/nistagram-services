package rs.ac.uns.ftn.nistagram.content.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.social.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
