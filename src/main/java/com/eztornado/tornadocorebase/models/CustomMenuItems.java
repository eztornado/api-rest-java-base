package com.eztornado.tornadocorebase.models;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Entity
@Table(name = "custom_menus_items") // Especifica el nombre de la tabla aquí
public class CustomMenuItems implements Comparable<CustomMenuItems> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;
    @Column(name = "content", nullable = false)
    private String value;
    @Column(name="href", nullable = true)
    private String to;
    private String color;

    private String icon;

    @Column(name = "item_order", nullable = false)
    private int order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roles_id", nullable = false)
    private Role role;
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT current_timestamp()")
    private Date created_at;
    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updated_at;
    @Column(name = "deleted_at")
    private Date deleted_at; // Campo para el borrado blando

    @ManyToOne(fetch = FetchType.LAZY) // Puedes usar EAGER dependiendo de tus necesidades
    @JoinColumn(name = "custom_menus_id", nullable = false)
    private CustomMenu customMenu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public CustomMenu getCustomMenu() {
        return customMenu;
    }

    public void setCustomMenu(CustomMenu customMenu) {
        this.customMenu = customMenu;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int compare(CustomMenuItems o1, CustomMenuItems o2) {
        int res = 0;
        if(o1.getOrder() > o2.getOrder()) res = 1;
        if(o1.getOrder() < o2.getOrder()) res = -1;
        return res;
    }

    @Override
    public int compareTo(CustomMenuItems other) {
        return Integer.compare(this.order, other.getOrder());
    }
}
