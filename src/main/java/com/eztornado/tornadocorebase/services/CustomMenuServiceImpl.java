package com.eztornado.tornadocorebase.services;

import com.eztornado.tornadocorebase.dto.CustomMenuDto;
import com.eztornado.tornadocorebase.exceptions.CustomMenuNotFoundException;
import com.eztornado.tornadocorebase.exceptions.UserNotFoundException;
import com.eztornado.tornadocorebase.models.CustomMenu;
import com.eztornado.tornadocorebase.models.User;
import com.eztornado.tornadocorebase.repositories.CustomMenuRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CustomMenuServiceImpl implements CustomMenuService {

    private final CustomMenuRepository repository;

    public CustomMenuServiceImpl(CustomMenuRepository repository) {
        this.repository = repository;
    }

    @Override
    public CustomMenu create(CustomMenuDto dto) {
        CustomMenu customMenu = new CustomMenu();
        customMenu.setName(dto.getName());
        customMenu.setCustomMenuItems(dto.getCustomMenuItems());
        customMenu = this.repository.save(customMenu);
        return customMenu;
    }

    @Override
    public Optional<CustomMenu> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Optional<CustomMenu> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public CustomMenu save(Long id, CustomMenuDto dto) {
        Optional<CustomMenu> customMenu = repository.findById(dto.getId());
        if(customMenu == null) {
            throw new CustomMenuNotFoundException(dto.getId());
        }
        CustomMenu customMenuObj = customMenu.get();
        customMenuObj.setCustomMenuItems(dto.getCustomMenuItems());
        customMenuObj.setName(dto.getName());
        customMenuObj = this.repository.save(customMenuObj);
        return customMenuObj;
    }

    @Override
    public void delete(Long id) {
        // Puedes verificar primero si el usuario existe
        if (!repository.existsById(id)) {
            throw new CustomMenuNotFoundException(id);
        }
        repository.deleteById(id);
    }

    @Override
    public void softDelete(Long id) {
        Optional<CustomMenu> customMenu = this.findById(id);
        if(customMenu == null) {
            throw new CustomMenuNotFoundException(id);
        }
        CustomMenu customMenuObj = customMenu.get();
        customMenuObj.setDeleted_at(new Date());
        repository.save(customMenuObj);
    }
}
