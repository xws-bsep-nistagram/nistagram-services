package rs.ac.uns.ftn.nistagram.user.graph.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.stereotype.Indexed;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Node
public class User {

    @Id
    private String username;
    private ProfileType profileType;
    @Relationship(type = "FOLLOWS")
    private Set<User> follows;
    @Relationship(type = "SENT_FOLLOW_REQUEST")
    private Set<User> pendingFollows;
    @Relationship(type = "HAS_MUTED")
    private Set<User> mutedUsers;
    @Relationship(type = "HAS_BLOCKED")
    private Set<User> blockedUsers;


    public void addFollowing(User following) {
        if (following.getProfileType() == ProfileType.PRIVATE)
            addPendingFollow(following);
        else
            addFollow(following);
    }

    private void addFollow(User following) {
        if (follows == null)
            follows = new HashSet<>();

        follows.add(following);
    }

    private void addPendingFollow(User following) {
        if (pendingFollows == null)
            pendingFollows = new HashSet<>();

        pendingFollows.add(following);
    }

    public void block(User blocking) {
        if(blockedUsers == null)
            blockedUsers = new HashSet<>();

        addBlocked(blocking);
    }

    public void unblock(User blocking) {
        blockedUsers.remove(blocking);
    }

    private void addBlocked(User user){
        if(blockedUsers == null)
            blockedUsers = new HashSet<>();
        blockedUsers.add(user);
    }

    private Boolean hasFollows(){
        return follows != null && !follows.isEmpty();
    }

    private Boolean followsUser(User user){
        return follows.contains(user);
    }
}
