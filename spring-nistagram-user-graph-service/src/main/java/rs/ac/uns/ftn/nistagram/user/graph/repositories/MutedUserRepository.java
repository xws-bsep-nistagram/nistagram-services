package rs.ac.uns.ftn.nistagram.user.graph.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import rs.ac.uns.ftn.nistagram.user.graph.domain.User;

import java.util.List;

public interface MutedUserRepository extends Neo4jRepository<User, String> {
    @Query("RETURN EXISTS ((:User{username:$0})-[:HAS_MUTED]->(:User))")
    Boolean hasMutedUsers(String username);

    @Query("OPTIONAL MATCH (n:User{username:$0})-[:HAS_MUTED]->(f:User) Return f")
    List<User> findMutedUsers(String username);

    @Query("RETURN EXISTS( (:User {username: $0})-[:HAS_MUTED]->(:User {username: $1}) )")
    Boolean mutedUser(String subject, String target);

    @Query("RETURN EXISTS( (:User {username: $0})-[:HAS_MUTED]->(:User {username: $1}) )")
    Boolean hasMuted(String subject, String target);

    @Query("MATCH (n1:User{username:$0}) MATCH (n2:User{username:$1}) CREATE (n1)-[:HAS_MUTED]->(n2)")
    void mute(String subject, String target);

    @Query("MATCH (n1:User{ username:$0 })-[r:HAS_MUTED]->(n2:User{ username:$1 }) DELETE r")
    void unmute(String subject, String target);
}
