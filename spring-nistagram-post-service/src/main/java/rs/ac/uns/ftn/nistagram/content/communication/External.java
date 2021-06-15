package rs.ac.uns.ftn.nistagram.content.communication;

import lombok.AllArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import rs.ac.uns.ftn.nistagram.content.exception.NotCloseFriendsException;
import rs.ac.uns.ftn.nistagram.content.exception.NotFollowException;

public class External {

    public static class BinaryQueryResponse {
        private boolean following;

        public void setFollowing(boolean following) { this.following = following; }
        public boolean getStatus() { return following; }
    }

    public static class ProfileVisibility {

        private boolean profilePrivate;

        public void setProfilePrivate(boolean profilePrivate) {
            this.profilePrivate = profilePrivate;
        }

        public boolean isProfilePrivate(){
            return profilePrivate;
        }
    }

    private static final String graphService = "user-graph-service";
    private static final String userService = "user-service";
    private static final String graphServiceDomain = "http://" + graphService + ":9004/";
    private static final String userServiceDomain = "http://" + userService + ":9003/";

    @FeignClient(name = graphService, url = graphServiceDomain + "api/user-graph/" )
    public interface GraphClient {

        @GetMapping("follows/{author}")
        BinaryQueryResponse checkFollowing(@RequestHeader("username") String caller,
                                           @PathVariable  String author);

        @GetMapping("{caller}/close-friends/{author}")
        BinaryQueryResponse checkCloseFriends(@PathVariable String caller, @PathVariable  String author);
    }

    @FeignClient(name = userService, url = userServiceDomain + "api/users/")
    public interface UserClient {

        @GetMapping("/visibility/{username}")
        ProfileVisibility isPrivate(@PathVariable String username);


    }


    @Component
    @AllArgsConstructor
    public static class GraphClientWrapper {
        private final GraphClient graphClient;

        public void assertFollow(String follower, String followed) {
            if (follower.equals(followed)) return;
            boolean follows = graphClient.checkFollowing(follower, followed).getStatus();
            if (!follows) throw new NotFollowException(follower, followed);
        }

        public void assertCloseFriends(String follower, String followed) {
            if (follower.equals(followed)) return;
            boolean closeFriends = graphClient.checkCloseFriends(follower, followed).getStatus();
            if (!closeFriends) throw new NotCloseFriendsException(follower, followed);
        }
    }

    @Component
    @AllArgsConstructor
    public static class UserClientWrapper {

        private UserClient userClient;

        public boolean isPrivate(String username){
            return userClient.isPrivate(username).isProfilePrivate();
        }

    }

}
