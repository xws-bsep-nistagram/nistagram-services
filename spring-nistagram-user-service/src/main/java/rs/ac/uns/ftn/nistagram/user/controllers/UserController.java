package rs.ac.uns.ftn.nistagram.user.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.user.controllers.dtos.ProfileBannedResponse;
import rs.ac.uns.ftn.nistagram.user.controllers.dtos.RegistrationRequestDTO;
import rs.ac.uns.ftn.nistagram.user.controllers.dtos.profile.*;
import rs.ac.uns.ftn.nistagram.user.controllers.mappers.ProfileMapper;
import rs.ac.uns.ftn.nistagram.user.controllers.mappers.RegistrationRequestMapper;
import rs.ac.uns.ftn.nistagram.user.domain.user.User;
import rs.ac.uns.ftn.nistagram.user.domain.user.UserStats;
import rs.ac.uns.ftn.nistagram.user.repository.specification.UserSpecification;
import rs.ac.uns.ftn.nistagram.user.service.ProfileService;
import rs.ac.uns.ftn.nistagram.user.service.RegistrationService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
public class UserController {

    private final RegistrationService registrationService;
    private final ProfileService profileService;
    private final ProfileMapper profileMapper;
    private final RegistrationRequestMapper registrationRequestMapper;

    @PostMapping("register")
    public ResponseEntity<String> register(@Valid @RequestBody RegistrationRequestDTO request) {
        registrationService.register(registrationRequestMapper.toDomain(request));
        return ResponseEntity.ok("Registration requested");
    }

    @GetMapping("profile")
    public ProfileViewDTO getProfile(@RequestHeader("username") String username) {
        User found = profileService.get(username);
        return profileMapper.map(found);
    }

    @DeleteMapping("ban/{username}")
    public PublicDataDTO ban(@PathVariable String username) {
        User bannedUser = profileService.ban(username);
        return profileMapper.mapPersonalData(bannedUser);
    }

    @GetMapping("{usernameQuery}")
    public List<PublicDataDTO> find(@RequestHeader("username") String caller,
                                    @PathVariable String usernameQuery) {
        List<User> foundUsers = profileService.find(usernameQuery, caller);
        return foundUsers
                .stream()
                .map(profileMapper::mapPersonalData)
                .collect(Collectors.toList());
    }

    @GetMapping("/visibility/{username}")
    public ProfileVisibilityDTO isPrivate(@PathVariable String username) {
        return new ProfileVisibilityDTO(profileService.isPrivate(username));
    }

    @GetMapping("/banned/{username}")
    public ProfileBannedResponse isBanned(@PathVariable String username) {
        return new ProfileBannedResponse(profileService.isBanned(username));
    }

    @GetMapping("taggable/{usernameQuery}")
    public List<PublicDataDTO> findTaggable(@RequestHeader("username") String caller,
                                            @PathVariable String usernameQuery) {
        List<User> foundUsers = profileService.findTaggable(usernameQuery, caller);
        return foundUsers
                .stream()
                .map(profileMapper::mapPersonalData)
                .collect(Collectors.toList());
    }

    @GetMapping("public/{username}")
    public PublicDataDTO getPublicData(@PathVariable String username) {
        User found = profileService.get(username);
        return profileMapper.mapPersonalData(found);
    }

    @GetMapping("profile/privacy")
    public PrivacyDataViewDTO getPrivacyData(@RequestHeader("username") String username) {
        User found = profileService.get(username);
        return profileMapper.map(found.getPrivacyData());
    }

    @GetMapping("profile/preferences")
    public NotificationPreferencesViewDTO getNotificationPreferences(@RequestHeader("username") String username) {
        User found = profileService.get(username);
        return profileMapper.map(found.getNotificationPreferences());
    }

    @PutMapping("profile")
    public ProfileViewDTO updateProfile(@RequestHeader("username") String username, @RequestBody ProfileUpdateDTO update) {
        User updated = profileService.update(username, profileMapper.map(update));
        return profileMapper.map(updated);
    }

    @PutMapping("profile/privacy")
    public PrivacyDataViewDTO updatePrivacyData(
            @RequestHeader("username") String username,
            @RequestBody PrivacyDataUpdateDTO update) {
        User updated = profileService.update(username, profileMapper.map(update));
        return profileMapper.map(updated.getPrivacyData());
    }

    @PutMapping("profile/preferences")
    public NotificationPreferencesViewDTO updateNotificationPreferences(
            @RequestHeader("username") String username,
            @RequestBody NotificationPreferencesUpdateDTO update) {
        User updated = profileService.update(username, profileMapper.map(update));
        return profileMapper.map(updated.getNotificationPreferences());
    }

    @GetMapping("profile/stats/{username}")
    public ProfileStatsDTO getProfileStats(@PathVariable String username) {
        UserStats stats = profileService.getStats(username);
        return profileMapper.map(stats);
    }

    @GetMapping("query")
    public List<String> queryUsers(@RequestParam Map<String, String> queryMap) {
        List<User> found = profileService.findBySpecification(new UserSpecification(queryMap));
        return found.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }

}
