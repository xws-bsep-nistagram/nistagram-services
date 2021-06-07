package rs.ac.uns.ftn.nistagram.feed.controllers.mappers;

import rs.ac.uns.ftn.nistagram.feed.controllers.payload.FeedResponse;
import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.PostFeedEntry;
import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.StoryFeedEntry;

public class FeedPayloadMapper {

    public static FeedResponse toDto(PostFeedEntry postFeedEntry){
        return FeedResponse
                .builder()
                .id(postFeedEntry.getId())
                .contentId(postFeedEntry.getPostId())
                .createdAt(postFeedEntry.getCreatedAt())
                .publisher(postFeedEntry.getPublisher())
                .build();

    }

    public static FeedResponse toDto(StoryFeedEntry storyFeedEntry){
        return FeedResponse
                .builder()
                .id(storyFeedEntry.getId())
                .contentId(storyFeedEntry.getId())
                .createdAt(storyFeedEntry.getCreatedAt())
                .publisher(storyFeedEntry.getPublisher())
                .build();
    }

}
