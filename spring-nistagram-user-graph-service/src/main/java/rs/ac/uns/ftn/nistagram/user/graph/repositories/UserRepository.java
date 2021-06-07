package rs.ac.uns.ftn.nistagram.user.graph.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.nistagram.user.graph.domain.ProfileType;
import rs.ac.uns.ftn.nistagram.user.graph.domain.User;


@Repository
public interface UserRepository extends Neo4jRepository<User, String> {
    Boolean existsByUsername(String username);

    @Query("MATCH (u:User {username: $0}) SET u.profileType = $1 RETURN u")
    User update(String username, ProfileType profileType);
}
