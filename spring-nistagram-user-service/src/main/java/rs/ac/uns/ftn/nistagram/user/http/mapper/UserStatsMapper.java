package rs.ac.uns.ftn.nistagram.user.http.mapper;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.user.domain.user.UserStats;
import rs.ac.uns.ftn.nistagram.user.http.graph.FollowerStats;
import rs.ac.uns.ftn.nistagram.user.http.post.PostStats;

@Component
public class UserStatsMapper {

    public UserStats map(FollowerStats followerStats, PostStats postStats) {
        UserStats stats = new UserStats();
        stats.setFollowers(followerStats.getFollowers());
        stats.setFollowing(followerStats.getFollowing());
        stats.setPostCount(postStats.getPostCount());
        return stats;
    }

}
