package rs.ac.uns.ftn.nistagram.feed.controllers.payload;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedResponseGroupEntry {

    private Long id;
    private Long contentId;
    private LocalDateTime createdAt;

    public FeedResponseGroupEntry(FeedResponse feedResponse) {
        this.id = feedResponse.getId();
        this.contentId = feedResponse.getContentId();
        this.createdAt = feedResponse.getCreatedAt();
    }
}
