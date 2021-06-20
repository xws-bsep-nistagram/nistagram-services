package rs.ac.uns.ftn.nistagram.user.controllers.mappers;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.user.controllers.dtos.*;
import rs.ac.uns.ftn.nistagram.user.domain.user.PersonalData;
import rs.ac.uns.ftn.nistagram.user.domain.user.PrivacyData;
import rs.ac.uns.ftn.nistagram.user.domain.user.User;
import rs.ac.uns.ftn.nistagram.user.domain.user.UserStats;
import rs.ac.uns.ftn.nistagram.user.domain.user.preferences.NotificationPreferences;

import java.time.LocalDate;

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
                notificationPreferences.getContent().getDislikes(),
                notificationPreferences.getContent().getShares(),
                notificationPreferences.getContent().getPhotosOfMe(),
                notificationPreferences.getFollowing().isFollowerRequestNotificationEnabled(),
                notificationPreferences.getFollowing().isFollowRequestAcceptedNotificationEnabled(),
                notificationPreferences.getFollowing().isNewFollowerNotificationEnabled(),
                notificationPreferences.getMessage().isMessageRequestNotificationEnabled(),
                notificationPreferences.getMessage().isMessageNotificationEnabled());
    }

    public PersonalData map(ProfileUpdateDTO dto) {
        PersonalData newData = new PersonalData();
        newData.setFullName(dto.getFullName());
        newData.setEmail(dto.getEmail());
        newData.setPhoneNumber(dto.getPhoneNumber());
        newData.setBio(dto.getBio());
        newData.setGender(dto.getGender());
        newData.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));
        newData.setWebsite(dto.getWebsite());
        return newData;
    }

    public PrivacyData map(PrivacyDataUpdateDTO dto) {
        PrivacyData newData = new PrivacyData();
        newData.setProfilePrivate(dto.isProfilePrivate());
        newData.setTaggable(dto.isTaggable());
        newData.setMessagesFromNonFollowersAllowed(dto.isMessagesFromNonFollowersAllowed());
        return newData;
    }

    public NotificationPreferences map(NotificationPreferencesUpdateDTO dto) {
        NotificationPreferences newPreferences = new NotificationPreferences();
        NotificationPreferences.Content content = new NotificationPreferences.Content();
        NotificationPreferences.Message message = new NotificationPreferences.Message();
        NotificationPreferences.Following following = new NotificationPreferences.Following();
        content.setComment(dto.getComment());
        content.setLikes(dto.getLikes());
        content.setDislikes(dto.getDislikes());
        content.setShares(dto.getShares());
        content.setPhotosOfMe(dto.getPhotosOfMe());
        message.setMessageNotificationEnabled(dto.isMessageNotificationEnabled());
        message.setMessageRequestNotificationEnabled(dto.isMessageRequestNotificationEnabled());
        following.setFollowerRequestNotificationEnabled(dto.isFollowerRequestNotificationEnabled());
        following.setNewFollowerNotificationEnabled(dto.isNewFollowerNotificationEnabled());
        following.setFollowRequestAcceptedNotificationEnabled(dto.isFollowRequestAcceptedNotificationEnabled());
        newPreferences.setContent(content);
        newPreferences.setFollowing(following);
        newPreferences.setMessage(message);
        return newPreferences;
    }

    public PublicDataDTO mapPersonalData(User found) {
        return new PublicDataDTO(
                found.getUsername(),
                found.getPersonalData().getFullName(),
                found.getPersonalData().getBio(),
                found.getPersonalData().getWebsite()
        );
    }

    public ProfileStatsDTO map(UserStats stats) {
        return new ProfileStatsDTO(stats.getFollowing(), stats.getFollowers(), stats.getPostCount());
    }
}
