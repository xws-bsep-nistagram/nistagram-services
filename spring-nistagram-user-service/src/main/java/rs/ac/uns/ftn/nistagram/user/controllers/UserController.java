package rs.ac.uns.ftn.nistagram.user.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.user.controllers.dtos.*;
import rs.ac.uns.ftn.nistagram.user.controllers.mappers.ProfileMapper;
import rs.ac.uns.ftn.nistagram.user.controllers.mappers.RegistrationRequestMapper;
import rs.ac.uns.ftn.nistagram.user.domain.user.User;
import rs.ac.uns.ftn.nistagram.user.service.ProfileService;
import rs.ac.uns.ftn.nistagram.user.service.RegistrationService;

import javax.validation.Valid;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final RegistrationService registrationService;
    private final ProfileService profileService;
    private final ProfileMapper profileMapper;
    private final RegistrationRequestMapper registrationRequestMapper;

    public UserController(RegistrationService registrationService, ProfileService profileService, ProfileMapper profileMapper, RegistrationRequestMapper registrationRequestMapper) {
        this.registrationService = registrationService;
        this.profileService = profileService;
        this.profileMapper = profileMapper;
        this.registrationRequestMapper = registrationRequestMapper;
    }

    @PostMapping("register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegistrationRequestDTO request) {
        String jwt = registrationService.register(registrationRequestMapper.toDomain(request));
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
    }

    @GetMapping("profile")
    public ProfileViewDTO getProfile(@RequestHeader("username") String username) {
        User found = profileService.get(username);
        return profileMapper.map(found);
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
    public ProfileViewDTO updateProfile(@RequestHeader("username") String username, ProfileUpdateDTO update) {
        User updated = profileService.update(username, profileMapper.map(update));
        return profileMapper.map(updated);
    }

    @PutMapping("profile/privacy")
    public PrivacyDataViewDTO updatePrivacyData(
            @RequestHeader("username") String username,
            PrivacyDataUpdateDTO update) {
        User updated = profileService.update(username, profileMapper.map(update));
        return profileMapper.map(updated.getPrivacyData());
    }

    @PutMapping("profile/preferences")
    public NotificationPreferencesViewDTO updateNotificationPreferences(
            @RequestHeader("username") String username,
            NotificationPreferencesUpdateDTO update) {
        User updated = profileService.update(username, profileMapper.map(update));
        return profileMapper.map(updated.getNotificationPreferences());
    }

}
