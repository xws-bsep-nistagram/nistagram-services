package rs.ac.uns.ftn.nistagram.feed.domain.entry.campaign;

import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.FeedEntry;
import rs.ac.uns.ftn.nistagram.feed.domain.user.User;

import javax.persistence.Entity;

@Entity
public class PostCampaignEntry extends FeedEntry {

    private Long postCampaignId;

    public void addUser(User user){
        super.addUser(user);
    }

    public void removeUser(User user){
        super.removeUser(user);
    }

}
