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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @EqualsAndHashCode.Include
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

    public void removeFromFeed(PostFeedEntry postFeedEntry) {
        postFeedEntries.remove(postFeedEntry);
    }
}
