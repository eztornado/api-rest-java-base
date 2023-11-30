package com.eztornado.tornadocorebase.services;

import com.eztornado.tornadocorebase.models.ERole;
import com.eztornado.tornadocorebase.models.Role;

import java.util.Optional;

public interface RoleService {
    Role create(Role roleDto);
    Optional<Role> findByName(ERole name);
    Optional<Role> findById(Long id);
    Role save(Long id, Role roleDto);
    void delete(Long id);
}
