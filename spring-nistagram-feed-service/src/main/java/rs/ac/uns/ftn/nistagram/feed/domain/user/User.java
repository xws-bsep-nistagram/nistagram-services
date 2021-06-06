package rs.ac.uns.ftn.nistagram.feed.domain.user;

import lombok.*;
import rs.ac.uns.ftn.nistagram.feed.domain.entry.campaign.PostCampaignEntry;
import rs.ac.uns.ftn.nistagram.feed.domain.entry.campaign.StoryCampaignEntry;
import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.PostFeedEntry;
import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.StoryFeedEntry;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String username;
    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<PostFeedEntry> postFeedEntries;
    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<StoryFeedEntry> storyFeedEntries;
    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<PostCampaignEntry> postCampaignEntries;
    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<StoryCampaignEntry> storyCampaignEntries;

    public void addToFeed(PostFeedEntry postFeedEntry) {
        postFeedEntries.add(postFeedEntry);
    }
}
