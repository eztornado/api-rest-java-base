package com.eztornado.tornadocorebase.repositories;

import com.eztornado.tornadocorebase.models.ERole;
import com.eztornado.tornadocorebase.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}