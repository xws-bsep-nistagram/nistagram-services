package rs.ac.uns.ftn.nistagram.content.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select p from Post p where p.author = ?1 and (p.ad=false or p.adApproved=true)")
    List<Post> getByUsername(String username);

    @Query("select count(p) from Post p where p.author = :username and (p.ad=false or p.adApproved=true)")
    Long getCountByUsername(@Param("username") String username);

    @Query(value = "select p from Post p where p.location.name = :street and (p.ad=false or p.adApproved=true)")
    List<Post> getByLocation(@Param("street") String street);

    @Query(value = "select p from Post p left join p.tags t where t.tag = :username and (p.ad=false or p.adApproved=true)")
    List<Post> getByTagged(@Param("username") String username);

    @Query(value = "select p from Post p right join p.userInteractions ui where ui.username = :username and (p.ad=false or p.adApproved=true)")
    List<Post> getLikedAndDislikedByUser(@Param("username") String username);
}
