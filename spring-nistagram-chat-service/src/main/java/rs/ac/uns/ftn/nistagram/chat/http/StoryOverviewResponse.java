package rs.ac.uns.ftn.nistagram.chat.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoryOverviewResponse {

    private long id;
    private boolean isReshare;
    private String mediaUrl;
    private PostOverviewResponse postOverviewResponse;

}
