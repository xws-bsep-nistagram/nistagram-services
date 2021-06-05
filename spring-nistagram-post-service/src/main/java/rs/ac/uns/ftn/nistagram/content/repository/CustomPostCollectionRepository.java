package rs.ac.uns.ftn.nistagram.content.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.collection.CustomPostCollection;

import java.util.Optional;

@Repository
public interface CustomPostCollectionRepository extends JpaRepository<CustomPostCollection, Long> {

    @Query(value = "select c from CustomPostCollection c where c.owner = ?1 and c.name = ?2")
    Optional<CustomPostCollection> getByUserAndName(String username, String collectionName);
}
