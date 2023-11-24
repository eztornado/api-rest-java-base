package com.eztornado.tornadocorebase.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class FileToken {
    // ... otros campos ...

    private LocalDateTime expiryDate;
    private String token;
    private Long fileId;

    @ManyToOne(fetch = FetchType.LAZY) // Puedes usar EAGER dependiendo de tus necesidades
    @JoinColumn(name = "users_id", nullable = false)
    private User user;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}