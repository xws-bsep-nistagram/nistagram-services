package rs.ac.uns.ftn.nistagram.campaign.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.campaign.domain.Campaign;
import rs.ac.uns.ftn.nistagram.campaign.domain.TargetedGroup;
import rs.ac.uns.ftn.nistagram.campaign.http.UserClient;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class UserService {

    private final UserClient client;

    public List<String> findMatchingUsers(Campaign campaign) {
        if (campaign.getTargetedGroup() == null) {
            campaign.setTargetedGroup(new TargetedGroup());
        }
        log.info("Fetching users that fit the criteria: {}", campaign.getTargetedGroup().toString());
        List<String> fetchedUsers = client.getUsers(campaign.getTargetedGroup().toQueryMap());
        log.info("Fetched target group for campaign '{}'", campaign.getName());
        return fetchedUsers;
    }

}
