package rs.ac.uns.ftn.nistagram.content.controller.dto.output;

import lombok.*;
import rs.ac.uns.ftn.nistagram.content.domain.core.UserContent;
import rs.ac.uns.ftn.nistagram.content.domain.core.post.Post;
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

    private List<String> mediaUrls;

    @Builder
    public PostOverviewDTO(long id, String author, LocalDateTime time,
                            String caption, Location location){
        super(author, time, caption, location);
        this.id = id;
    }


    public void addMediaUrl(String url) {
        if(mediaUrls == null)
            mediaUrls = new ArrayList<>();
        mediaUrls.add(url);
    }
}
