package rs.ac.uns.ftn.nistagram.notification.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import rs.ac.uns.ftn.nistagram.notification.http.payload.NotificationPreferencesDTO;

@FeignClient(name = "user-service", url = "http://user-service:9003/api/users")
public interface UserClient {

    @GetMapping("profile/preferences")
    NotificationPreferencesDTO getNotificationPreferences(@RequestHeader("username") String username);

}
