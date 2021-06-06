package rs.ac.uns.ftn.nistagram.user.graph.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.nistagram.user.graph.domain.User;

import java.util.List;

@Repository
public interface UserRepository extends Neo4jRepository<User, String> {

    Boolean existsByUsername(String username);

    @Query("RETURN EXISTS( (:User {username: $0})-[:HAS_BLOCKED]->(:User {username: $1}) )")
    Boolean hasBlocked(String subject, String target);

    @Query("RETURN EXISTS( (:User {username: $0})-[:FOLLOWS]->(:User {username: $1}) )")
    Boolean isFollowing(String subject, String target);

    @Query("OPTIONAL MATCH (n:User{username:$0})<-[:FOLLOWS]-(f:User) Return f")
    List<User> findFollowers(String username);

    @Query("RETURN EXISTS ((:User{username:$0})<-[:FOLLOWS]-(:User))")
    Boolean hasFollowers(String username);

    @Query("MATCH (n1:User{username:$0}) MATCH (n2:User{username:$1}) CREATE (n1)-[:FOLLOWS]->(n2)")
    void follow(String subject, String target);

    @Query("MATCH (n1:User{ username:$0 })-[r:FOLLOWS]->(n2:User{ username:$1 }) DELETE r")
    void unfollow(String subject, String target);

    @Query("RETURN EXISTS ((:User{username:$0})-[:HAS_BLOCKED]->(:User))")
    Boolean hasBlockedUsers(String username);

    @Query("OPTIONAL MATCH (n:User{username:$0})-[:HAS_BLOCKED]->(f:User) Return f")
    List<User> findBlockedUsers(String username);

    @Query("MATCH (n1:User{username:$0}) MATCH (n2:User{username:$1}) CREATE (n1)-[:HAS_BLOCKED]->(n2)")
    void block(String subject, String target);

    @Query("MATCH (n1:User{ username:$0 })-[r:HAS_BLOCKED]->(n2:User{ username:$1 }) DELETE r")
    void unblock(String subject, String target);

    @Query("RETURN EXISTS( (:User {username: $0})-[:SENT_FOLLOW_REQUEST]->(:User {username: $1}) )")
    Boolean sentFollowRequest(String subject, String target);

    @Query("MATCH (n1:User{ username:$0 })-[r:SENT_FOLLOW_REQUEST]->(n2:User{ username:$1 }) DELETE r")
    void removeFollowRequest(String subject, String target);

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

    @Query("RETURN EXISTS ((:User{username:$0})-[:FOLLOWS]->(:User))")
    Boolean hasFollowings(String username);

    @Query("OPTIONAL MATCH (n:User{username:$0})-[:FOLLOWS]->(f:User) Return f")
    List<User> findFollowings(String username);

    @Query("RETURN EXISTS ((:User{username:$0})<-[:SENT_FOLLOW_REQUEST]-(:User))")
    Boolean hasPendingFollowers(String username);

    @Query("OPTIONAL MATCH (n:User{username:$0})<-[:SENT_FOLLOW_REQUEST]-(f:User) Return f")
    List<User> findPendingFollowings(String username);

    @Query("MATCH (n1:User{username:$0}) MATCH (n2:User{username:$1}) CREATE (n1)-[:SENT_FOLLOW_REQUEST]->(n2)")
    void sendFollowRequest(String subject, String target);

    @Query("RETURN EXISTS( (:User {username: $0})-[:IS_CLOSE_FRIEND]->(:User {username: $1}) )")
    Boolean isCloseFriend(String subject, String target);

    @Query("MATCH (n1:User{username:$0}) MATCH (n2:User{username:$1}) CREATE (n1)-[:IS_CLOSE_FRIEND]->(n2)")
    void addToCloseFriends(String subject, String target);

    @Query("MATCH (n1:User{ username:$0 })-[r:IS_CLOSE_FRIEND]->(n2:User{ username:$1 }) DELETE r")
    void removeFromCloseFriends(String subject, String target);

    @Query("OPTIONAL MATCH (n:User{username:$0})<-[:IS_CLOSE_FRIEND]-(f:User) Return f")
    List<User> findCloseFriends(String username);

}
