package rs.ac.uns.ftn.nistagram.content.domain.core;

import lombok.*;
import rs.ac.uns.ftn.nistagram.content.domain.locale.Location;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class UserContent {
    private String author;
    private LocalDateTime time;
    private String caption;
    private Location location;
}
