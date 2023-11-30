package com.eztornado.tornadocorebase.dto;

import com.eztornado.tornadocorebase.models.Role;
import com.eztornado.tornadocorebase.models.User;
import com.eztornado.tornadocorebase.security.services.UserDetailsImpl;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;

public class UserDto {

    private Long id;
    @NotBlank(message = "You must fill the email")
    private String email;
    @NotBlank(message = "You must fill the username")
    private String username;
    private Boolean active = false;
    private String password;

    private HashSet<Role> roles;
    // otros campos...

    // Getters y setters...
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HashSet<Role> getRoles() {
        return roles;
    }

    public void setRoles(HashSet<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", active=" + active +
                ", password='" + password + '\'' +
                '}';
    }

    //Inicialización del usuario a través de un UserDetailsImpl
    public UserDto(UserDetailsImpl user) {
        this.id  = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.active = user.isActive();
        this.email = user.getEmail();
    }
    public UserDto(User user) {
        this.id  = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.active = user.isActive();
        this.email = user.getEmail();
    }

    public UserDto() {
        // Constructor predeterminado
    }
}
