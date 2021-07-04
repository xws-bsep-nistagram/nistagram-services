package rs.ac.uns.ftn.nistagram.campaign.http.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class Post {
    private Long id;
    private String author;
    private String caption;
    private List<MediaLink> mediaUrls;
    private List<String> tags;

    public Post(String author, String caption, List<MediaLink> mediaUrls) {
        this.author = author;
        this.caption = caption;
        this.mediaUrls = mediaUrls;
        this.tags = new ArrayList<>();
    }
}
