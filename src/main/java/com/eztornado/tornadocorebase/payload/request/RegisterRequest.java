package com.eztornado.tornadocorebase.payload.request;

import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {
    @NotBlank(message = "You must fill user name")
    private String name;
    private String email;
    private String password;

    // Getters y setters

    public String getName() {
        return name;
    }

    public void setName(String nombre) {
        this.name = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}