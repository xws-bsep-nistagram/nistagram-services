package rs.ac.uns.ftn.nistagram.notification.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.notification.controllers.mappers.NotificationMapper;
import rs.ac.uns.ftn.nistagram.notification.services.NotificationService;

@Controller
@RequestMapping("/api/notification")
@AllArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<?> get(@RequestHeader("username") String username) {
        return ResponseEntity.ok(notificationService.get(username)
                .stream()
                .map(NotificationMapper::toDto));
    }

    @DeleteMapping("{notificationId}")
    public ResponseEntity<?> delete(@RequestHeader("username") String username, @PathVariable Long notificationId) {
        return ResponseEntity.ok(NotificationMapper.toDto(notificationService.hide(username, notificationId)));
    }
}
