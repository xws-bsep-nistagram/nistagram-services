package rs.ac.uns.ftn.nistagram.user.graph.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;


import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}
