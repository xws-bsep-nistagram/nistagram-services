package rs.ac.uns.ftn.nistagram.content.communication;

import lombok.AllArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class External {

    public static class BinaryQueryResponse {
        private boolean following;

        public void setFollowing(boolean following) { this.following = following; }
        public boolean getStatus() { return following; }
    }

    private static final String graphService = "user-graph-service";
    private static final String graphServiceDomain = "http://" + graphService + ":9004/";

    @FeignClient(name = graphService, url = graphServiceDomain + "api/user-graph/" )
    public interface GraphClient {

        @GetMapping("{caller}/follows/{author}")
        BinaryQueryResponse checkFollowing(@PathVariable String caller, @PathVariable  String author);

        @GetMapping("{caller}/follows/{author}")
        BinaryQueryResponse checkCloseFriends(@PathVariable String caller, @PathVariable  String author);
    }

    @Component
    @AllArgsConstructor
    public static class GraphClientWrapper {
        private final GraphClient graphClient;

        public void assertFollow(String follower, String followed) {
            boolean follows = graphClient.checkFollowing(follower, followed).getStatus();
            if (!follows) throw new RuntimeException(follower + " does not follow " + followed);
        }

        public void assertCloseFriends(String follower, String followed) {
            boolean closeFriends = graphClient.checkCloseFriends(follower, followed).getStatus();
            if (!closeFriends) throw new RuntimeException(follower + " and " + followed + " are not close friends");
        }
    }
}
