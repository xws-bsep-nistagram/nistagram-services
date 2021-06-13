package rs.ac.uns.ftn.nistagram.auth.domain;

import rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions.AuthException;

import java.util.function.Function;


public class RegistrationRequest {

    private String username;
    private String password;
    private String email;

    public RegistrationRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
