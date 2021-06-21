package rs.ac.uns.ftn.nistagram.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Credentials implements UserDetails {
    @Id
    private String username;
    private String passwordHash;
    @Column(unique = true)
    private String email;
    private Boolean activated;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable
    private List<Role> roles;
    private String uuid;

    public Credentials(String username, String passwordHash, String email) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.activated = false;
        this.roles = List.of(new Role("ROLE_USER"));
        this.uuid = UUID.randomUUID().toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return activated;
    }

    public String getUuid() {
        return uuid;
    }

    public void activate() {
        this.activated = true;
    }

}
