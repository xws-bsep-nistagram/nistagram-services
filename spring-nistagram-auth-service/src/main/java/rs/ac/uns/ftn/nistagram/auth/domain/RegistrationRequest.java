package rs.ac.uns.ftn.nistagram.auth.domain;

import rs.ac.uns.ftn.nistagram.auth.infrastructure.exceptions.AuthException;

import java.util.function.Function;

public class RegistrationRequest {

    private String username;
    private String password;
    private String email;
    private boolean hashed;

    public RegistrationRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * Performs a hash function on the password field
     * @param hashFunction function that takes a String and produces a hashed String
     */
    public void hashPassword(Function<? super String, String> hashFunction) {
        if (hashed) throw new AuthException("Cannot hash an already hashed password!");
        this.password = hashFunction.apply(password);
        hashed = true;
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
}
