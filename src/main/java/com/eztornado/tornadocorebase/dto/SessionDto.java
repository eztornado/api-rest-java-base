package com.eztornado.tornadocorebase.dto;

import com.eztornado.tornadocorebase.security.services.UserDetailsImpl;

import java.util.Date;

public class SessionDto {

    private Long id;

    private UserDetailsImpl user;
    private String token;
    private String ip;

    private String client;

    private Date expiresAt;


    private Date created_at;


    private Date updated_at;


    private Date deleted_at; // Campo para el borrado blando

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDetailsImpl getUser() {
        return user;
    }

    public void setUser(UserDetailsImpl user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Date getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(Date deleted_at) {
        this.deleted_at = deleted_at;
    }

    @Override
    public String toString() {
        return "SessionDto{" +
                "id=" + id +
                ", user=" + user +
                ", token='" + token + '\'' +
                ", ip='" + ip + '\'' +
                ", client='" + client + '\'' +
                ", expiresAt=" + expiresAt +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", deleted_at=" + deleted_at +
                '}';
    }
}
