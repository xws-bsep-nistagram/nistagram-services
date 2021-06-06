package rs.ac.uns.ftn.nistagram.user.controllers.mappers;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.user.controllers.dtos.NotificationPreferencesViewDTO;
import rs.ac.uns.ftn.nistagram.user.controllers.dtos.PrivacyDataViewDTO;
import rs.ac.uns.ftn.nistagram.user.controllers.dtos.ProfileViewDTO;
import rs.ac.uns.ftn.nistagram.user.domain.user.PrivacyData;
import rs.ac.uns.ftn.nistagram.user.domain.user.User;
import rs.ac.uns.ftn.nistagram.user.domain.user.preferences.NotificationPreferences;

@Component
public class ProfileMapper {

    public ProfileViewDTO map(User user) {
        return new ProfileViewDTO(
                user.getUsername(),
                user.getPersonalData().getFullName(),
                user.getPersonalData().getEmail(),
                user.getPersonalData().getPhoneNumber(),
                user.getPersonalData().getGender(),
                user.getPersonalData().getDateOfBirth(),
                user.getPersonalData().getWebsite(),
                user.getPersonalData().getBio());
    }

    public PrivacyDataViewDTO map(PrivacyData privacyData) {
        return new PrivacyDataViewDTO(
                privacyData.isProfilePrivate(),
                privacyData.isMessagesFromNonFollowersAllowed(),
                privacyData.isTaggable());
    }

    public NotificationPreferencesViewDTO map(NotificationPreferences notificationPreferences) {
        return new NotificationPreferencesViewDTO(
                notificationPreferences.getContent().getComment(),
                notificationPreferences.getContent().getLikes(),
                notificationPreferences.getContent().getPhotosOfMe(),
                notificationPreferences.getFollowing().isFollowerRequestNotificationEnabled(),
                notificationPreferences.getFollowing().isFollowRequestAcceptedNotificationEnabled(),
                notificationPreferences.getFollowing().isNewFollowerNotificationEnabled(),
                notificationPreferences.getMessage().isMessageRequestNotificationEnabled(),
                notificationPreferences.getMessage().isMessageNotificationEnabled());
    }

}
