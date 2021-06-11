package rs.ac.uns.ftn.nistagram.auth.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class PasswordResetRequest {

    private static final Duration ACTIVE_DURATION = Duration.ofHours(6);
    @Id
    private String email;
    private String uuid;
    private LocalDateTime expires;
    private boolean reset;

    public PasswordResetRequest(String email) {
        this.email = email;
        this.expires = LocalDateTime.now().plus(ACTIVE_DURATION);
        this.uuid = UUID.randomUUID().toString();
        this.reset = false;
    }



}
