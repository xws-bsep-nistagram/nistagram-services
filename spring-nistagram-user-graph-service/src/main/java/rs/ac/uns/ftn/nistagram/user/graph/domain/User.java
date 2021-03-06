package rs.ac.uns.ftn.nistagram.user.graph.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;


@Node
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @EqualsAndHashCode.Include
    private String username;
    private ProfileType profileType;
    private RegistrationStatus registrationStatus;
    @Relationship(type = "FOLLOWS")
    private Set<User> follows;
    @Relationship(type = "SENT_FOLLOW_REQUEST")
    private Set<User> pendingFollows;
    @Relationship(type = "HAS_MUTED")
    private Set<User> mutedUsers;
    @Relationship(type = "HAS_BLOCKED")
    private Set<User> blockedUsers;
    @Relationship(type = "IS_CLOSE_FRIEND")
    private Set<User> closeFriends;


    public void addFollowing(User following) {
        if (following.getProfileType() == ProfileType.PRIVATE)
            addPendingFollow(following);
        else
            addFollow(following);
    }

    private void addFollow(User targetUser) {
        if (follows == null)
            follows = new HashSet<>();

        follows.add(targetUser);
    }

    private void addPendingFollow(User targetUser) {
        if (pendingFollows == null)
            pendingFollows = new HashSet<>();

        pendingFollows.add(targetUser);
    }

    public void addBlocked(User targetUser) {
        if (blockedUsers == null)
            blockedUsers = new HashSet<>();

        blockedUsers.add(targetUser);
    }

    public void addMuted(User targetUser) {
        if (mutedUsers == null)
            mutedUsers = new HashSet<>();

        mutedUsers.add(targetUser);
    }

    private Boolean hasFollows() {
        return follows != null && !follows.isEmpty();
    }

    private Boolean followsUser(User user) {
        return follows.contains(user);
    }

    public Boolean hasPrivateProfile() {
        return profileType.equals(ProfileType.PRIVATE);
    }

}
