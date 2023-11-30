package com.eztornado.tornadocorebase.models;

import com.eztornado.tornadocorebase.security.services.UserDetailsImpl;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.*;


@Entity
    @Table(name = "custom_menus") // Especifica el nombre de la tabla aquí
    public class CustomMenu {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        @CreatedDate
        @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT current_timestamp()")
        private Date created_at;
        @LastModifiedDate
        @Column(name = "updated_at")
        private Date updated_at;
        @Column(name = "deleted_at")
        private Date deleted_at; // Campo para el borrado blando

        @OneToMany(mappedBy = "customMenu", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

        public List<HashMap<String, String>> toJson(UserDetailsImpl authUser) {
            List<HashMap<String, String >> resultado = new ArrayList<>();
            if(!this.customMenuItems.isEmpty()) {
                Collections.sort(this.customMenuItems);
                for(CustomMenuItems item : this.customMenuItems) {
                    if(!item.getRole().getName().name().equals(authUser.getMainRole())) {
                        continue;
                    }
                    HashMap<String, String> itemResultado = new HashMap<String, String>();
                    itemResultado.put("label", item.getLabel());
                    itemResultado.put("value", item.getValue());
                    itemResultado.put("to", item.getTo());
                    itemResultado.put("icon", item.getIcon());
                    itemResultado.put("color", item.getColor());
                    System.out.println(itemResultado);
                    resultado.add(itemResultado);
                }
            }
            return resultado;
        }
}
