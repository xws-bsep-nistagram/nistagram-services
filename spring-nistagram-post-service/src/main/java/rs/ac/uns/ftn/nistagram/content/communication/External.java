package rs.ac.uns.ftn.nistagram.content.communication;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class External {

    public static class BinaryQueryResponse {
        private boolean following;

        public void setFollowing(boolean following) { this.following = following; }
        public boolean getStatus() { return following; }
    }

    private static final String graphService = "user-service";
    private static final String graphServiceDomain = "http://" + graphService + ":9004/";

    @FeignClient(name = graphService, url = graphServiceDomain + "api/user-graph/" )
    public interface GraphClient {

        @GetMapping("{sender}/follows/{author}")
        BinaryQueryResponse checkFollowing(@PathVariable String sender, @PathVariable  String author);

        @GetMapping("{sender}/follows/{author}")
        BinaryQueryResponse checkCloseFriends(@PathVariable String sender, @PathVariable  String author);
    }
}
