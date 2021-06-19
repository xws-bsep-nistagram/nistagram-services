package rs.ac.uns.ftn.nistagram.content.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select p from Post p where p.author = ?1")
    List<Post> getByUsername(String username);

    @Query("select count(p) from Post p where p.author = :username")
    Long getCountByUsername(@Param("username") String username);

    @Query(value = "select P from Post P where P.location.name = :street")
    List<Post> getByLocation(@Param("street") String street);

    // TODO Make a query to fetch all posts where the 'username' is tagged
    @Query(value = "select P from Post P left join P.tags T where T.tag = :username")
    List<Post> getByTagged(@Param("username") String username);
}
