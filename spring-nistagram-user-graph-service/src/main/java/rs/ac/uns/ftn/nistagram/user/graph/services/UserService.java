package rs.ac.uns.ftn.nistagram.user.graph.services;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.nistagram.user.graph.domain.User;
import rs.ac.uns.ftn.nistagram.user.graph.exceptions.EntityAlreadyExistsException;
import rs.ac.uns.ftn.nistagram.user.graph.exceptions.OperationNotPermittedException;
import rs.ac.uns.ftn.nistagram.user.graph.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public List<User> findFollowers(String username) {
        userPresenceCheck(username);

        if(!userRepository.hasFollowers(username)) {
            log.info("User {} has no followers", username);
            return new ArrayList<>();
        }

        List<User> followers = userRepository.findFollowers(username);
        log.info("Found {} followers for user {}", followers.size(), username);

        return followers;
    }

    @Transactional
    public void create(User user){
        log.info("Received a user creation request for a user {}", user.getUsername());
        userUniquenessCheck(user);
        User createdUser = userRepository.save(user);
        log.info("User {} has been successfully created", createdUser.getUsername());
    }
    @Transactional
    public void follow(String followerUsername, String followingUsername){
        log.info("Received a follow request from {} to {}",
                followerUsername,
                followingUsername);

        userPresenceCheck(followerUsername);
        userPresenceCheck(followingUsername);
        blockedConstraintCheck(followerUsername, followingUsername);
        alreadyFollowsCheck(followerUsername,followingUsername);

        var follower = userRepository.findById(followerUsername).get();
        var following = userRepository.findById(followingUsername).get();

        follower.addFollowing(following);
        userRepository.save(follower);
        log.info("User {} follows {}",
                followerUsername,
                followingUsername);
    }
    @Transactional
    public void unfollow(String followerUsername, String followingUsername) {

        log.info("Received a unfollow request from {} to {}",
                followerUsername,
                followingUsername);

        userPresenceCheck(followerUsername);
        userPresenceCheck(followingUsername);

        followingConstraintCheck(followerUsername, followingUsername);

        userRepository.unfollow(followerUsername,followingUsername);
        log.info("User {} is no longer following {}",
                followerUsername,
                followingUsername);

    }

    private void blockedConstraintCheck(String followerUsername, String followingUsername) {
        if(userRepository.hasBlocked(followerUsername, followingUsername)) {
            var message = String.format("User %s is blocked by user %s",
                    followerUsername,
                    followingUsername);
            log.warn(message);
            throw new OperationNotPermittedException(message);
        }
    }

    private void userPresenceCheck(String username){
        if(!userRepository.existsByUsername(username)){
            var message = String.format("User %s doesn't exist", username);
            log.warn(message);
            throw new EntityAlreadyExistsException(message);
        }
    }

    private void userUniquenessCheck(User user) {
        if(userRepository.existsByUsername(user.getUsername())) {
            var message = String.format("User %s already exists", user.getUsername());
            log.warn(message);
            throw new EntityAlreadyExistsException(message);
        }
    }

    private void followingConstraintCheck(String followerUsername, String followingUsername) {
        if(!userRepository.isFollowing(followerUsername, followingUsername)) {
            var message = String.format("User %s doesn't follow user %s",
                    followerUsername,
                    followingUsername);
            log.warn(message);
            throw new OperationNotPermittedException(message);
        }
    }
    private void alreadyFollowsCheck(String followerUsername, String followingUsername){
        if(userRepository.isFollowing(followerUsername, followingUsername)) {
            var message = String.format("User %s already follows user %s",
                    followerUsername,
                    followingUsername);
            log.warn(message);
            throw new OperationNotPermittedException(message);
        }
    }

}
