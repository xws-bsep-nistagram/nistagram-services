package rs.ac.uns.ftn.nistagram.feed.messaging.mappers.campaign;

import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.PostFeedEntry;
import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.StoryFeedEntry;
import rs.ac.uns.ftn.nistagram.feed.messaging.payload.campaign.CampaignPayload;

public class CampaignPayloadMapper {

    public static PostFeedEntry toPost(CampaignPayload payload) {
        return new PostFeedEntry(payload.getCampaignId());
    }

    public static StoryFeedEntry toStory(CampaignPayload payload) {
        return new StoryFeedEntry(payload.getCampaignId());
    }

}
