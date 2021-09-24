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
    private boolean banned;
    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<PostFeedEntry> postFeedEntries;
    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<StoryFeedEntry> storyFeedEntries;
    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<PostCampaignEntry> postCampaignEntries;
    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<StoryCampaignEntry> storyCampaignEntries;

    public void ban(){
        banned = true;
    }

    public void unban(){
        banned = false;
    }

    public void addToPostFeed(PostFeedEntry postFeedEntry) {
        postFeedEntries.add(postFeedEntry);
    }

    public void removeFromPostFeed(PostFeedEntry postFeedEntry) {
        postFeedEntries.remove(postFeedEntry);
    }

    public void addToStoryFeed(StoryFeedEntry storyFeedEntry){
        storyFeedEntries.add(storyFeedEntry);
    }

    public void removeFromStoryFeed(StoryFeedEntry storyFeedEntry){
        storyFeedEntries.remove(storyFeedEntry);
    }

}
