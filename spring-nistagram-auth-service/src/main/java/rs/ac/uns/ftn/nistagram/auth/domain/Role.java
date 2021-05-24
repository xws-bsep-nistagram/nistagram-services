package rs.ac.uns.ftn.nistagram.auth.domain;

import java.util.ArrayList;
import java.util.List;

public class Role {
    private final List<Permission> allowedPermissions = new ArrayList<>();
    private String id;
}
