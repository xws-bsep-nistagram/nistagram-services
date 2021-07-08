package rs.ac.uns.ftn.nistagram.user.graph.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import rs.ac.uns.ftn.nistagram.user.graph.domain.User;

import java.util.List;

public interface FollowerRepository extends Neo4jRepository<User, String> {

    @Query("MATCH (user:User {username: $0})-[:FOLLOWS]->(m)-[:FOLLOWS]->(recommendation) " +
            "WHERE NOT (user)-[:FOLLOWS]->(recommendation) " +
            "AND NOT (user)-[:SENT_FOLLOW_REQUEST]->(recommendation) " +
            "RETURN DISTINCT recommendation")
    List<User> recommend(String subject);

    @Query("MATCH (user:User {username: $0})-[:FOLLOWS]->(following)-[:FOLLOWS]->(target: User{username: $1}) " +
            "RETURN following")
    List<User> findMutualConnection(String subject, String target);

    @Query("MATCH (user:User)<-[r:FOLLOWS]-(follower:User) " +
            "WHERE NOT (:User {username: $0})-[:FOLLOWS]->(user) " +
            "AND NOT (:User {username: $0})-[:SENT_FOLLOW_REQUEST]->(user)" +
            "RETURN user,count(r) ORDER BY count(r) DESC;")
    List<User> findAllOrderedByPopularity(String username);

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

    @Query("OPTIONAL MATCH (n:User{username:$0})<-[:FOLLOWS]-(f:User) Return count(f)")
    Long findFollowerCount(String subject);

    @Query("OPTIONAL MATCH (n:User{username:$0})-[:FOLLOWS]->(f:User) Return count(f)")
    Long findFollowingCount(String subject);
}
