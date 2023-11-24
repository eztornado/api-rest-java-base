package com.eztornado.tornadocorebase.models;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Entity
@Table(name = "sessions") // Especifica el nombre de la tabla aquí
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Puedes usar EAGER dependiendo de tus necesidades
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    private String token;
    private String ip;

    private String client;

    private Date expiresAt;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT current_timestamp()")
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Date UpdatedAt;

    @Column(name = "deleted_at")
    private Date deletedAt; // Campo para el borrado blando

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date created_at) {
        this.createdAt = created_at;
    }

    public Date getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(Date updated_at) {
        this.UpdatedAt = updated_at;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deleted_at) {
        this.deletedAt = deleted_at;
    }


    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", user=" + user +
                ", token='" + token + '\'' +
                ", ip='" + ip + '\'' +
                ", client='" + client + '\'' +
                ", expiresAt=" + expiresAt +
                ", created_at=" + createdAt +
                ", updated_at=" + UpdatedAt +
                ", deleted_at=" + deletedAt +
                '}';
    }
}
