package rs.ac.uns.ftn.nistagram.feed.controllers.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class FeedResponse {

    private Long id;
    private Long contentId;
    private String publisher;
    private LocalDateTime createdAt;

}
