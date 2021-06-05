package rs.ac.uns.ftn.nistagram.content.domain.core;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.content.domain.locale.Location;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class UserContent {
    private String author;
    private LocalDateTime time;
    private String caption;
    private Location location;
}
