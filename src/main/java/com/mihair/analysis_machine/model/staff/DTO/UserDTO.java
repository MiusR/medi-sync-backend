package com.mihair.analysis_machine.model.staff.DTO;

public class UserDTO {
    private String username;
    private String passwordENC;
    private String role;

    public UserDTO(String username, String passwordENC, String role) {
        this.username = username;
        this.passwordENC = passwordENC;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordENC() {
        return passwordENC;
    }

    public void setPasswordENC(String passwordENC) {
        this.passwordENC = passwordENC;
    }
}
