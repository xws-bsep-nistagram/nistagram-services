package rs.ac.uns.ftn.nistagram.post.domain.content;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.post.domain.locale.Location;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@MappedSuperclass
public class UserContent {
    private String author;
    private LocalDateTime time;
    private String caption;
    private Location location;
}
