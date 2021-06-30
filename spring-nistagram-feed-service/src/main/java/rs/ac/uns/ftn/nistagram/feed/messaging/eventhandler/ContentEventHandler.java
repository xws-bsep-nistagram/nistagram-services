package rs.ac.uns.ftn.nistagram.feed.messaging.eventhandler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.feed.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.feed.messaging.event.post.PostCreatedEvent;
import rs.ac.uns.ftn.nistagram.feed.messaging.event.post.PostDeletedEvent;
import rs.ac.uns.ftn.nistagram.feed.messaging.event.story.StoryCreatedEvent;
import rs.ac.uns.ftn.nistagram.feed.messaging.event.story.StoryDeletedEvent;
import rs.ac.uns.ftn.nistagram.feed.messaging.mappers.post.PostTopicPayloadMapper;
import rs.ac.uns.ftn.nistagram.feed.messaging.mappers.story.StoryTopicPayloadMapper;
import rs.ac.uns.ftn.nistagram.feed.messaging.util.Converter;
import rs.ac.uns.ftn.nistagram.feed.services.FeedService;

@Slf4j
@Component
@AllArgsConstructor
public class ContentEventHandler {

    private final FeedService feedService;
    private final Converter converter;

    @RabbitListener(queues = RabbitMQConfig.POST_CREATED_EVENT)
    public void handlePostCreated(@Payload String payload) {

        log.debug("Handling a post created event {}", payload);

        PostCreatedEvent event = converter.toObject(payload, PostCreatedEvent.class);

        feedService.addToPostFeeds(PostTopicPayloadMapper
                .toPostFeedEntry(event.getPostEventPayload()));

    }

    @RabbitListener(queues = RabbitMQConfig.POST_DELETED_EVENT)
    public void handlePostDeleted(@Payload String payload) {

        log.debug("Handling a post deleted event {}", payload);

        PostDeletedEvent event = converter.toObject(payload, PostDeletedEvent.class);

        feedService.removeFromPostFeeds(PostTopicPayloadMapper
                .toPostFeedEntry(event.getPostEventPayload()));

    }

    @RabbitListener(queues = RabbitMQConfig.STORY_CREATED_EVENT)
    public void handleStoryCreated(@Payload String payload) {

        log.debug("Handling a story created event {}", payload);

        StoryCreatedEvent event = converter.toObject(payload, StoryCreatedEvent.class);

        feedService.addToStoryFeeds(StoryTopicPayloadMapper
                .toStoryFeedEntry(event.getStoryEventPayload()));

    }

    @RabbitListener(queues = RabbitMQConfig.STORY_DELETED_EVENT)
    public void handleStoryDeleted(@Payload String payload) {

        log.debug("Handling a story deleted event {}", payload);

        StoryDeletedEvent event = converter.toObject(payload, StoryDeletedEvent.class);

        feedService.removeFromStoryFeeds(StoryTopicPayloadMapper
                .toStoryFeedEntry(event.getStoryEventPayload()));

    }
}
