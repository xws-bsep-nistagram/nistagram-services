package rs.ac.uns.ftn.nistagram.feed.http.payload.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class ContentResponse {

    private long id;
    private String author;
    private LocalDateTime time;

}
