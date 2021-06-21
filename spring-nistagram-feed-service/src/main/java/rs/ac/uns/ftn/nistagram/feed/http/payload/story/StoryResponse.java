package rs.ac.uns.ftn.nistagram.feed.http.payload.story;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.feed.http.payload.post.ContentResponse;

@NoArgsConstructor
@Getter
@Setter
public class StoryResponse extends ContentResponse {

    private boolean closeFriends;

}
