package rs.ac.uns.ftn.nistagram.auth.domain;

import java.util.List;

public class Credentials {
    private String username;
    private String passwordHash;
    private String email;
    private Boolean activated;
    private List<Role> roles;
}
