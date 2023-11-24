package com.eztornado.tornadocorebase.payload.request;

import jakarta.validation.constraints.NotBlank;

public class RecoveryPasswordAskRequest {
    @NotBlank
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }
}
