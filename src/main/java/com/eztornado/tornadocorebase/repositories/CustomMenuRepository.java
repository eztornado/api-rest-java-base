package com.eztornado.tornadocorebase.repositories;

import com.eztornado.tornadocorebase.models.CustomMenu;
import com.eztornado.tornadocorebase.models.ERole;
import com.eztornado.tornadocorebase.models.File;
import com.eztornado.tornadocorebase.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomMenuRepository extends JpaRepository<CustomMenu, Long> {
    // Query methods if needed
    Optional<CustomMenu> findByName(String  name);
}
