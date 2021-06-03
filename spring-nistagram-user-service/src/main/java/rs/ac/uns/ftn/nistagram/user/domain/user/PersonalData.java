package rs.ac.uns.ftn.nistagram.user.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class PersonalData {
    private String fullName;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String website;
    private String bio;
}
