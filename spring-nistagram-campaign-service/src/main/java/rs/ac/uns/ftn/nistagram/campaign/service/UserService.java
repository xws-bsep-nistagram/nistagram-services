package rs.ac.uns.ftn.nistagram.campaign.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.nistagram.campaign.domain.Campaign;
import rs.ac.uns.ftn.nistagram.campaign.http.UserClient;
import rs.ac.uns.ftn.nistagram.campaign.http.UserQuery;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class UserService {

    private final UserClient client;

    public List<String> findMatchingUsers(Campaign campaign) {
        // TODO: Create a query for target groups
        UserQuery query = UserQuery.builder().build();

        List<String> fetchedUsers = client.getUsers(query);
        log.info("Fetched target group for campaign '{}'", campaign.getName());
        return fetchedUsers;
    }

}
