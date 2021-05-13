package rs.ac.uns.ftn.nistagram.user.domain.user;

import java.util.ArrayList;
import java.util.List;

public class Role {

    private String id;
    private final List<Permission> allowedPermissions = new ArrayList<>();

}
