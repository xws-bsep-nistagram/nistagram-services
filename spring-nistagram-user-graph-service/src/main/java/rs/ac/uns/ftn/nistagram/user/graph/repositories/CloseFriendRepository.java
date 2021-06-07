package rs.ac.uns.ftn.nistagram.user.graph.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import rs.ac.uns.ftn.nistagram.user.graph.domain.User;

import java.util.List;

public interface CloseFriendRepository extends Neo4jRepository<User, String> {
    @Query("RETURN EXISTS( (:User {username: $0})-[:IS_CLOSE_FRIEND]->(:User {username: $1}) )")
    Boolean isCloseFriend(String subject, String target);

    @Query("MATCH (n1:User{username:$0}) MATCH (n2:User{username:$1}) CREATE (n1)-[:IS_CLOSE_FRIEND]->(n2)")
    void addToCloseFriends(String subject, String target);

    @Query("MATCH (n1:User{ username:$0 })-[r:IS_CLOSE_FRIEND]->(n2:User{ username:$1 }) DELETE r")
    void removeFromCloseFriends(String subject, String target);

    @Query("OPTIONAL MATCH (n:User{username:$0})<-[:IS_CLOSE_FRIEND]-(f:User) Return f")
    List<User> findCloseFriends(String username);
}
