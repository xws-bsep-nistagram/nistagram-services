package rs.ac.uns.ftn.nistagram.feed.controllers.payload;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedResponseGroup {

    private String publisher;
    private List<FeedResponseGroupEntry> entries;

    public FeedResponseGroup(String publisher){
        this.publisher = publisher;
    }

    public void setEntries(List<FeedResponse> responses){
        entries = new ArrayList<>();
        responses.forEach( feedResponse ->
                entries.add(new FeedResponseGroupEntry(feedResponse)));

    }

}
