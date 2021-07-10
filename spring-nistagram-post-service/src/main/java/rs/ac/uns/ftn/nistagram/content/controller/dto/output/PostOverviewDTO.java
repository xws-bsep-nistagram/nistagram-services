package rs.ac.uns.ftn.nistagram.content.controller.dto.output;

import lombok.*;
import rs.ac.uns.ftn.nistagram.content.domain.core.UserContent;
import rs.ac.uns.ftn.nistagram.content.domain.locale.Location;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostOverviewDTO extends UserContent {

    private long id;
    private List<UserInteractionOverviewDTO> userInteractions;
    private List<CommentOverviewDTO> comments;
    private List<String> tags;
    private List<String> mediaUrls;
    private boolean ad;

    @Builder
    public PostOverviewDTO(long id, String author, LocalDateTime time,
                           String caption, Location location, boolean ad) {
        super(author, time, caption, location);
        this.id = id;
        this.ad = ad;
    }


    public void addMediaUrl(String url) {
        if (mediaUrls == null)
            mediaUrls = new ArrayList<>();
        mediaUrls.add(url);
    }

    public void addComment(CommentOverviewDTO dto) {
        if (this.comments == null)
            this.comments = new ArrayList<>();
        this.comments.add(dto);
    }

    public void addInteraction(UserInteractionOverviewDTO dto) {
        if (this.userInteractions == null)
            this.userInteractions = new ArrayList<>();
        this.userInteractions.add(dto);
    }

    public void addTag(String tag) {
        if (this.tags == null)
            this.tags = new ArrayList<>();
        this.tags.add(tag);
    }
}
