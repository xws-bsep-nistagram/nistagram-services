package rs.ac.uns.ftn.nistagram.feed.http.mappers;

import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.PostFeedEntry;
import rs.ac.uns.ftn.nistagram.feed.domain.entry.feed.StoryFeedEntry;
import rs.ac.uns.ftn.nistagram.feed.http.payload.post.ContentResponse;
import rs.ac.uns.ftn.nistagram.feed.http.payload.story.StoryResponse;

public class UserContentMapper {

    public static PostFeedEntry toDomain(ContentResponse contentResponse){
        return new PostFeedEntry(contentResponse.getAuthor(),
                contentResponse.getTime(),
                contentResponse.getId());
    }

    public static StoryFeedEntry toDomain(StoryResponse storyResponse){
        return new StoryFeedEntry(storyResponse.getAuthor(),
                storyResponse.getTime(),
                storyResponse.getId(),
                storyResponse.isCloseFriends());
    }
}
