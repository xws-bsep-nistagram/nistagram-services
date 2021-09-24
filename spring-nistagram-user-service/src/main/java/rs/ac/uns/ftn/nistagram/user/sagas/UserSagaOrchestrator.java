package rs.ac.uns.ftn.nistagram.user.sagas;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.user.domain.user.User;
import rs.ac.uns.ftn.nistagram.user.messaging.config.RabbitMQConfig;
import rs.ac.uns.ftn.nistagram.user.messaging.event.UserBanEvent;
import rs.ac.uns.ftn.nistagram.user.messaging.util.Converter;
import rs.ac.uns.ftn.nistagram.user.repository.UserRepository;
import rs.ac.uns.ftn.nistagram.user.sagas.domain.UserBannedReply;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class UserSagaOrchestrator {
    private final UserRepository userRepository;
    private final RabbitTemplate rabbitTemplate;
    private final Converter converter;

    public void executeBanSaga(User user){

        log.info("Banning an user with an username: '{}'", user.getUsername());

        UserBanEvent event = new UserBanEvent(UUID.randomUUID().toString(),
                user.getUsername());

        executeBanOperation(event, RabbitMQConfig.USER_BANNED_GRAPH_CHANNEL);

    }

    @Transactional
    @RabbitListener(queues = {RabbitMQConfig.USER_BAN_SAGA_REPLY_CHANNEL})
    public void listenToReplyChannel(@Payload String payload) {

        log.info("Message from user ban saga reply channel received, message: {}", payload);

        UserBannedReply replyMessage = converter.toObject(payload, UserBannedReply.class);

        UserBanEvent event = new UserBanEvent(UUID.randomUUID().toString(),
                replyMessage.getUsername());

        if(replyMessage.graphChannelBanSucceeded()) {
            executeBanOperation(event, RabbitMQConfig.USER_BANNED_FEED_CHANNEL);
            return;
        }
        else if(replyMessage.graphChannelBanFailed())
            return;

        if(replyMessage.feedChannelBanSucceeded()) {
            executeBanOperation(event, RabbitMQConfig.USER_BANNED_NOTIFICATION_CHANNEL);
            return;
        }
        else if(replyMessage.feedChannelBanFailed()) {
            executeUnbanOperation(event, RabbitMQConfig.USER_UNBAN_GRAPH_CHANNEL);
            return;
        }

        if(replyMessage.notificationChannelBanSucceeded()) {
            executeBanOperation(event, RabbitMQConfig.USER_BANNED_POST_CHANNEL);
            return;
        }
        else if(replyMessage.notificationChannelBanFailed()) {
            executeUnbanOperation(event, RabbitMQConfig.USER_UNBAN_FEED_CHANNEL);
            executeUnbanOperation(event, RabbitMQConfig.USER_UNBAN_GRAPH_CHANNEL);
            return;
        }

        if(replyMessage.postChannelBanFailed()) {
            executeUnbanOperation(event, RabbitMQConfig.USER_UNBAN_NOTIFICATION_CHANNEL);
            executeUnbanOperation(event, RabbitMQConfig.USER_UNBAN_FEED_CHANNEL);
            executeUnbanOperation(event, RabbitMQConfig.USER_UNBAN_GRAPH_CHANNEL);
            return;
        }

        banUser(replyMessage.getUsername());
        log.info("User ban saga successfully executed, user with an username '{}' successfully banned", replyMessage.getUsername());


    }

    void banUser(String username) {

        User found = userRepository.getOne(username);
        found.ban();
        userRepository.save(found);

    }

    private void executeBanOperation(UserBanEvent event, String channel) {

        log.info("Sending user banned event to {}, event: {}", channel, event);

        rabbitTemplate.convertAndSend(channel, converter.toJSON(event));
    }
    private void executeUnbanOperation(UserBanEvent event, String channel){
        log.info("Sending user unbanned event to {}, event: {}", channel, event);

        rabbitTemplate.convertAndSend(channel, converter.toJSON(event));
    }
}
