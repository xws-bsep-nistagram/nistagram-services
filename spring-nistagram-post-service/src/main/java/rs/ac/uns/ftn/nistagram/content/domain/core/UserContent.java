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
    private boolean hidden;

    public UserContent(String author, LocalDateTime time, String caption, Location location) {
        this.author = author;
        this.time = time;
        this.caption = caption;
        this.location = location;
    }

    public void hide(){
        hidden = true;
    }

    public void unhide(){
        hidden = false;
    }
}
