package rs.ac.uns.ftn.nistagram.user.graph.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import rs.ac.uns.ftn.nistagram.user.graph.domain.User;

import java.util.List;

public interface BlockedUserRepository extends Neo4jRepository<User, String> {
    @Query("RETURN EXISTS ((:User{username:$0})-[:HAS_BLOCKED]->(:User))")
    Boolean hasBlockedUsers(String username);

    @Query("RETURN EXISTS( (:User {username: $0})-[:HAS_BLOCKED]->(:User {username: $1}) )")
    Boolean hasBlocked(String subject, String target);

    @Query("OPTIONAL MATCH (n:User{username:$0})-[:HAS_BLOCKED]->(f:User) Return f")
    List<User> findBlockedUsers(String username);

    @Query("MATCH (n1:User{username:$0}) MATCH (n2:User{username:$1}) CREATE (n1)-[:HAS_BLOCKED]->(n2)")
    void block(String subject, String target);

    @Query("MATCH (n1:User{ username:$0 })-[r:HAS_BLOCKED]->(n2:User{ username:$1 }) DELETE r")
    void unblock(String subject, String target);
}
