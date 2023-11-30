package com.eztornado.tornadocorebase.dto;

import com.eztornado.tornadocorebase.models.CustomMenuItems;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;

public class CustomMenuDto {

    private Long id;

    private String name;


    private Date created_at;

    private Date updated_at;

    private Date deleted_at; // Campo para el borrado blando

    private List<CustomMenuItems> customMenuItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<CustomMenuItems> getCustomMenuItems() {
        return customMenuItems;
    }

    public void setCustomMenuItems(List<CustomMenuItems> customMenuItems) {
        this.customMenuItems = customMenuItems;
    }
}
