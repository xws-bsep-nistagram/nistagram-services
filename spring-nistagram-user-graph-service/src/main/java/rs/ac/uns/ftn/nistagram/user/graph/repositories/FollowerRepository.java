package rs.ac.uns.ftn.nistagram.user.graph.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import rs.ac.uns.ftn.nistagram.user.graph.domain.User;

import java.util.List;

public interface FollowerRepository extends Neo4jRepository<User, String> {
    @Query("RETURN EXISTS( (:User {username: $0})-[:FOLLOWS]->(:User {username: $1}) )")
    Boolean isFollowing(String subject, String target);

    @Query("OPTIONAL MATCH (n:User{username:$0})<-[:FOLLOWS]-(f:User) Return f")
    List<User> findFollowers(String username);

    @Query("RETURN EXISTS ((:User{username:$0})<-[:FOLLOWS]-(:User))")
    Boolean hasFollowers(String username);

    @Query("RETURN EXISTS ((:User{username:$0})-[:FOLLOWS]->(:User))")
    Boolean hasFollowings(String username);

    @Query("OPTIONAL MATCH (n:User{username:$0})-[:FOLLOWS]->(f:User) Return f")
    List<User> findFollowings(String username);

    @Query("RETURN EXISTS ((:User{username:$0})<-[:SENT_FOLLOW_REQUEST]-(:User))")
    Boolean hasPendingFollowers(String username);

    @Query("OPTIONAL MATCH (n:User{username:$0})<-[:SENT_FOLLOW_REQUEST]-(f:User) Return f")
    List<User> findPendingFollowings(String username);

    @Query("RETURN EXISTS( (:User {username: $0})-[:SENT_FOLLOW_REQUEST]->(:User {username: $1}) )")
    Boolean sentFollowRequest(String subject, String target);

    @Query("MATCH (n1:User{ username:$0 })-[r:SENT_FOLLOW_REQUEST]->(n2:User{ username:$1 }) DELETE r")
    void removeFollowRequest(String subject, String target);

    @Query("MATCH (n1:User{username:$0}) MATCH (n2:User{username:$1}) CREATE (n1)-[:SENT_FOLLOW_REQUEST]->(n2)")
    void sendFollowRequest(String subject, String target);

    @Query("MATCH (n1:User{username:$0}) MATCH (n2:User{username:$1}) CREATE (n1)-[:FOLLOWS]->(n2)")
    void follow(String subject, String target);

    @Query("MATCH (n1:User{ username:$0 })-[r:FOLLOWS]->(n2:User{ username:$1 }) DELETE r")
    void unfollow(String subject, String target);
}
