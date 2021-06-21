package rs.ac.uns.ftn.nistagram.auth.controllers.dtos;

import java.util.List;

public class RolesDTO {

    private List<String> roles;

    public RolesDTO(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

}
