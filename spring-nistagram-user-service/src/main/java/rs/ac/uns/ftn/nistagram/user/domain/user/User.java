package rs.ac.uns.ftn.nistagram.user.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.user.domain.user.preferences.NotificationPreferences;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class User {

    @Id
    private String username;
    @Embedded
    private PersonalData personalData;
    @Embedded
    private PrivacyData privacyData;
    @Embedded
    private AdministrativeData administrativeData;
    @Embedded
    private NotificationPreferences notificationPreferences;

    public User(String username, PersonalData personalData) {
        this.username = username;
        this.personalData = personalData;
        initializeNewUserDefaults();
    }

    private void initializeNewUserDefaults() {
        this.privacyData = new PrivacyData();
        this.administrativeData = new AdministrativeData();
        this.notificationPreferences = new NotificationPreferences();
    }

    public boolean isTaggable(){
        return privacyData.isTaggable();
    }

}
