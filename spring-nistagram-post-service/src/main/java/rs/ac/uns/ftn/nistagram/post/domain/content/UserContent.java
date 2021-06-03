package rs.ac.uns.ftn.nistagram.post.domain.content;

import rs.ac.uns.ftn.nistagram.post.domain.locale.Location;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public class UserContent {
    private String author;
    private LocalDateTime time;
    private String caption;
    private Location location;
    private List<String> hashTags;
}
