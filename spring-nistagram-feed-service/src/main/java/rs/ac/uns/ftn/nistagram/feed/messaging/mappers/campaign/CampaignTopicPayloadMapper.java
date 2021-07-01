package rs.ac.uns.ftn.nistagram.feed.messaging.mappers.campaign;

import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.PostFeedEntry;
import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.StoryFeedEntry;
import rs.ac.uns.ftn.nistagram.feed.messaging.payload.campaign.TargetedCampaignTopicPayload;

public class CampaignTopicPayloadMapper {

    public static PostFeedEntry toPost(TargetedCampaignTopicPayload payload) {
        return new PostFeedEntry(payload.getCampaignId());
    }

    public static StoryFeedEntry toStory(TargetedCampaignTopicPayload payload) {
        return new StoryFeedEntry(payload.getCampaignId());
    }

}
