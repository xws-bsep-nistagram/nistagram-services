package rs.ac.uns.ftn.nistagram.post.domain.content;

import rs.ac.uns.ftn.nistagram.post.domain.locale.Location;

import java.time.Instant;
import java.util.List;

public class UserContent {

    private String createdBy;
    private Instant createdAt;
    private String caption;
    private Location location;
    private List<String> hashTags;

}
