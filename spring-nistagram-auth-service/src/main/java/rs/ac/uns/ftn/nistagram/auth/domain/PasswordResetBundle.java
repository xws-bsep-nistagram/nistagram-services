package rs.ac.uns.ftn.nistagram.auth.domain;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
public class PasswordResetBundle {
    @NotEmpty
    @Length(min = 8)
    private String password;
    @NotEmpty
    private String uuid;

    public void setPassword(String password) {
        this.password = password;
    }
}
