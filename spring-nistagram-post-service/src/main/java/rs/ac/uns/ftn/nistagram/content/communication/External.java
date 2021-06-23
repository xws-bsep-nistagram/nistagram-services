package rs.ac.uns.ftn.nistagram.content.communication;

import lombok.AllArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import rs.ac.uns.ftn.nistagram.content.exception.NotCloseFriendsException;
import rs.ac.uns.ftn.nistagram.content.exception.NotFollowException;
import rs.ac.uns.ftn.nistagram.content.exception.UserBlockedException;

public class External {

    private static final String graphService = "user-graph-service";
    private static final String userService = "user-service";
    private static final String graphServiceDomain = "http://" + graphService + ":9004/";
    private static final String userServiceDomain = "http://" + userService + ":9003/";

    @FeignClient(name = graphService, url = graphServiceDomain + "api/user-graph/")
    public interface GraphClient {

        @GetMapping("follows/{target}")
        BinaryQueryResponse checkFollowing(@RequestHeader("username") String subject,
                                           @PathVariable String target);

        @GetMapping("/close-friends/{target}")
        CloseFriendRelationshipResponse checkCloseFriends(@RequestHeader("username") String subject,
                                                          @PathVariable String target);

        @GetMapping("/blocked/{target}")
        BlockedRelationshipResponse hasBlocked(@RequestHeader("username") String subject,
                                               @PathVariable String target);

        @GetMapping("/blocked-by/{subject}")
        BlockedRelationshipResponse isBlockedBy(@RequestHeader("username") String target,
                                                @PathVariable String subject);

    }

    @FeignClient(name = userService, url = userServiceDomain + "api/users/")
    public interface UserClient {

        @GetMapping("/visibility/{username}")
        ProfileVisibility isPrivate(@PathVariable String username);


    }

    public static class BinaryQueryResponse {
        private boolean following;

        public void setFollowing(boolean following) {
            this.following = following;
        }

        public boolean getStatus() {
            return following;
        }
    }

    public static class BlockedRelationshipResponse {
        private boolean blocked;

        public boolean isBlocked() {
            return this.blocked;
        }

        public void setBlocked(boolean blocked) {
            this.blocked = blocked;
        }
    }

    public static class CloseFriendRelationshipResponse {
        private boolean closeFriend;

        public boolean isCloseFriend() {
            return closeFriend;
        }

        public void setCloseFriend(boolean closeFriend) {
            this.closeFriend = closeFriend;
        }
    }

    public static class ProfileVisibility {

        private boolean profilePrivate;

        public boolean isProfilePrivate() {
            return profilePrivate;
        }

        public void setProfilePrivate(boolean profilePrivate) {
            this.profilePrivate = profilePrivate;
        }
    }

    @Component
    @AllArgsConstructor
    public static class GraphClientWrapper {
        private final GraphClient graphClient;

        public void assertFollow(String follower, String followed) {
            if (follower.equals(followed)) return;
            boolean follows = graphClient.checkFollowing(followed, follower).getStatus();
            if (!follows) throw new NotFollowException(followed, follower);
        }

        public void assertCloseFriends(String follower, String followed) {
            if (follower.equals(followed)) return;
            boolean closeFriends = graphClient.checkCloseFriends(follower, followed).isCloseFriend();
            if (!closeFriends) throw new NotCloseFriendsException(follower, followed);
        }

        public void assertBlocked(String subject, String target) {
            if (subject.equals(target)) return;
            if (graphClient.hasBlocked(subject, target).blocked)
                throw new UserBlockedException(subject, target);
            if (graphClient.isBlockedBy(target, subject).blocked)
                throw new UserBlockedException(subject, target);
        }

        public boolean blocked(String subject, String target) {
            if (subject.equals(target)) return false;
            return graphClient.hasBlocked(subject, target).blocked || graphClient.isBlockedBy(subject, target).blocked;
        }
    }

    @Component
    @AllArgsConstructor
    public static class UserClientWrapper {

        private UserClient userClient;

        public boolean isPrivate(String username) {
            return userClient.isPrivate(username).isProfilePrivate();
        }

    }


}
