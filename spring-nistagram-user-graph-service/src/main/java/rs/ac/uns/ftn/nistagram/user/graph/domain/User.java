package rs.ac.uns.ftn.nistagram.user.graph.domain;

import java.util.Set;

public class User {

    private String username;
    private ProfileType profileType;
    private Set<FollowRelationship> following;
    private Set<PendingFollowRelationship> pendingFollows;
    private Set<MutedRelationship> mutedUsers;
    private Set<BlockedRelationship> blockedUsers;

}
