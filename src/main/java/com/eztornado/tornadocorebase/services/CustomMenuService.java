package com.eztornado.tornadocorebase.services;

import com.eztornado.tornadocorebase.dto.CustomMenuDto;
import com.eztornado.tornadocorebase.models.CustomMenu;
import java.util.Optional;

public interface CustomMenuService {
    CustomMenu create(CustomMenuDto dto);
    Optional<CustomMenu> findByName(String name);
    Optional<CustomMenu> findById(Long id);
    CustomMenu save(Long id, CustomMenuDto dto);
    void delete(Long id);
    void softDelete(Long id);
}