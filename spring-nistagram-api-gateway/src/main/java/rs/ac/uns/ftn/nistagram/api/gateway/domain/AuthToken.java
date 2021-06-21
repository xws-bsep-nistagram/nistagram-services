package rs.ac.uns.ftn.nistagram.api.gateway.domain;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class AuthToken {

    private String username;
    private List<String> authorities;

    public String getUsername() {
        return username;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        return this.authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public Authentication getAuthentication() {
        return new UsernamePasswordAuthenticationToken(username, null, getAuthorities());
    }

}
