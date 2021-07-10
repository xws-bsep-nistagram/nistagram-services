package rs.ac.uns.ftn.nistagram.feed.controllers.mappers;

import rs.ac.uns.ftn.nistagram.feed.controllers.payload.FeedResponse;
import rs.ac.uns.ftn.nistagram.feed.controllers.payload.FeedResponseGroup;
import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.FeedEntry;
import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.PostFeedEntry;
import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.StoryFeedEntry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FeedPayloadMapper {

    public static FeedResponse toDto(PostFeedEntry postFeedEntry){
        return FeedResponse
                .builder()
                .id(postFeedEntry.getId())
                .contentId(postFeedEntry.getPostId())
                .createdAt(postFeedEntry.getCreatedAt())
                .publisher(postFeedEntry.getPublisher())
                .ad(postFeedEntry.isAd())
                .build();

    }

    public static FeedResponse toDto(StoryFeedEntry storyFeedEntry){
        return FeedResponse
                .builder()
                .id(storyFeedEntry.getId())
                .contentId(storyFeedEntry.getStoryId())
                .createdAt(storyFeedEntry.getCreatedAt())
                .publisher(storyFeedEntry.getPublisher())
                .ad(storyFeedEntry.isAd())
                .build();
    }

    public static List<FeedResponseGroup> group(List<FeedResponse> response){
        var groupedResponses = new ArrayList<FeedResponseGroup>();

        List<String> publishers = response
                .stream()
                .map(FeedResponse::getPublisher)
                .distinct()
                .collect(Collectors.toList());

        publishers.forEach(publisher -> {
            FeedResponseGroup feedResponseGroup = new FeedResponseGroup(publisher);

            List<FeedResponse> entriesByPublisher = response
                    .stream()
                    .filter(e -> e.getPublisher().equals(publisher))
                    .collect(Collectors.toList());

            feedResponseGroup.setEntries(entriesByPublisher);
            groupedResponses.add(feedResponseGroup);
        });

        return groupedResponses;
    }

}
